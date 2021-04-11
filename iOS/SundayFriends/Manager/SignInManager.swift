//
//  SignInManager.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit
import AuthenticationServices
import GoogleSignIn
import KeychainSwift

let CLIENT_ID = "930104015926-ksuiehvsl4o0g1dtp1lv4mm2pain05vs.apps.googleusercontent.com"
let USER_ID = "user_id"
let USER_NAME = "user_name"
let USER_EMAIL = "user_email"
let USER_SIGNIN_TYPE = "sign_in_type"

enum SignInRole {
  case user
  case admin
}

enum SignInStatus{
  case notSignedIn(_ message: String?)
  case signedIn(_ role: SignInRole)
}

class SignInManager: NSObject {
  static var shared: SignInManager = SignInManager()
  let keychain: KeychainSwift = KeychainSwift()
  
  let service: UserAPIInterface = AppUtils.MOCK_ENABLED ? UserAPIMock() : UserAPI()
  var isManualLogin: Bool = false
  typealias STATUS_HANDLER = ((_ status: SignInStatus) -> ())
  var handler: STATUS_HANDLER?
  var currentUser: User?
  var token: String = ""
  
  func kickOff(_ statusHandler: @escaping STATUS_HANDLER){
    self.handler = statusHandler
    
    // Google Sign in
    GIDSignIn.sharedInstance()?.clientID = CLIENT_ID
    GIDSignIn.sharedInstance()?.delegate = self
    GIDSignIn.sharedInstance()?.presentingViewController = UIApplication.shared.windows.first!.rootViewController!
    
    if let value = UserDefaults.standard.value(forKey: USER_SIGNIN_TYPE) as? String, value == "apple" {
      // Apple sign in
      signInWithApple()
      return
    }
    
    Loader.shared.start()
    GIDSignIn.sharedInstance()?.restorePreviousSignIn()
  }
  
  func signIn() {
    UserDefaults.standard.setValue("google", forKey: USER_SIGNIN_TYPE)
    SignInManager.shared.isManualLogin = true
    GIDSignIn.sharedInstance()?.presentingViewController = UIApplication.shared.windows.first!.rootViewController!
    GIDSignIn.sharedInstance()?.signIn()
    Loader.shared.start()
  }
  
  func signInWithApple() {
    UserDefaults.standard.setValue("apple", forKey: USER_SIGNIN_TYPE)
    let appleIDProvider = ASAuthorizationAppleIDProvider()
    let request = appleIDProvider.createRequest()
    request.requestedScopes = [.fullName, .email]
    
    let authorizationController = ASAuthorizationController(authorizationRequests: [request])
    authorizationController.delegate = self
    authorizationController.presentationContextProvider = self
    authorizationController.performRequests()
    Loader.shared.start()
  }
  
  func signOut() {
    self.currentUser = nil
    UIApplication.shared.windows.first?.rootViewController = SignInViewController.userInstance
    UserDefaults.standard.removeObject(forKey: USER_SIGNIN_TYPE)
    GIDSignIn.sharedInstance()?.signOut()
  }
  
}

extension SignInManager: ASAuthorizationControllerPresentationContextProviding, ASAuthorizationControllerDelegate {
  func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
    return UIApplication.shared.windows.first!
  }
  
  func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
    if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
      guard let appleIDToken = appleIDCredential.identityToken else {
        print("Unable to fetch identity token")
        if let handler = handler { handler(.notSignedIn(nil)) }
        Loader.shared.stop()
        return
      }
      
      guard let idTokenString = String(data: appleIDToken, encoding: .utf8) else {
        print("Unable to serialize token string from data: \(appleIDToken.debugDescription)")
        if let handler = handler { handler(.notSignedIn(nil)) }
        Loader.shared.stop()
        return
      }
      
      let userIdentifier = appleIDCredential.user
      let fullName = getFullName(components: appleIDCredential.fullName)
      let email = appleIDCredential.email
      
      // Recurring sign in - fetch creds from storage
      guard let name = fullName, let myEmail = email else {
        // match stored id with the current sign in - for subsequent sign in's
        if let storedUserId = SignInManager.shared.keychain.get(USER_ID), storedUserId == userIdentifier, let storedEmail = SignInManager.shared.keychain.get(USER_EMAIL) {
          let storedFullName = SignInManager.shared.keychain.get(USER_NAME) ?? storedEmail
          initializeOnboarding(name: storedFullName, email: storedEmail, imageUrl: nil, token: idTokenString)
        } else {
          // logout user
          if let handler = handler { handler(.notSignedIn(nil)) }
          Loader.shared.stop()
        }
        return
      }

      // first time sign in
      SignInManager.shared.keychain.set(name, forKey: USER_NAME)
      SignInManager.shared.keychain.set(myEmail, forKey: USER_EMAIL)
      SignInManager.shared.keychain.set(userIdentifier, forKey: USER_ID)

      initializeOnboarding(name: name, email: myEmail, imageUrl: nil, token: idTokenString)
    }
  }
  
  func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
    if let handler = handler { handler(.notSignedIn(error.localizedDescription)) }
    Loader.shared.stop()
  }
  
  
  private func getFullName(components: PersonNameComponents?) -> String? {
    guard let givenName = components?.givenName else {
      return components?.familyName
    }
    guard let familyName = components?.familyName else {
      return givenName
    }
    return givenName + " " + familyName
  }
  
  func initializeOnboarding(name: String, email: String, imageUrl: String?, token: String) {
    SignInManager.shared.token = token
    
    let user = User()
    user.name = name
    user.email = email
    user.imageUrl = imageUrl
    SignInManager.shared.currentUser = user
    
    DispatchQueue.global(qos: .background).async {
      self.service.onboardUser(emailId: email, user: user) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
          switch result {
          case .success(let user):
            SignInManager.shared.currentUser = user
            if user.admin {
              if let handler = self.handler { handler(.signedIn(.admin))}
            } else if let active = user.active, active{
              if let handler = self.handler { handler(.signedIn(.user))}
            } else {
              if let handler = self.handler { handler(.notSignedIn("sf.error.sometingWentWrong".localized))}
            }
          case .failure(let error):
            if let err = error as? UserError {
              if let handler = self.handler { handler(.notSignedIn(err.description))}
            } else {
              if let handler = self.handler { handler(.notSignedIn(nil))}
            }
          }
          SignInManager.shared.isManualLogin = false
        }
      }
    }
  }
  
}

// MARK: - Google Sign in
extension SignInManager: GIDSignInDelegate {
  
  func sign(_ signIn: GIDSignIn!, didSignInFor user: GIDGoogleUser!, withError error: Error!) {
    if let _ = error {
      if let handler = handler { handler(.notSignedIn(nil)) }
      Loader.shared.stop()
      return
    }
    
    let name = user.profile.name
    let email = user.profile.email
    let imageUrl = user.profile.imageURL(withDimension: 100)
    let auth = user.authentication
    if nil != name, nil != email, nil != auth {
      initializeOnboarding(name: name!, email: email!, imageUrl: imageUrl?.absoluteString, token: auth!.idToken)
    } else {
      if let handler = handler { handler(.notSignedIn(nil)) }
      Loader.shared.stop()
    }
  }
  
  func sign(_ signIn: GIDSignIn!, didDisconnectWith user: GIDGoogleUser!, withError error: Error!) {
    
  }
}
