//
//  SignInManager.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit
import GoogleSignIn

let CLIENT_ID = "930104015926-ksuiehvsl4o0g1dtp1lv4mm2pain05vs.apps.googleusercontent.com"

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
    Loader.shared.start()
    GIDSignIn.sharedInstance()?.restorePreviousSignIn()
  }
  
  func signIn() {
    SignInManager.shared.isManualLogin = true
    GIDSignIn.sharedInstance()?.presentingViewController = UIApplication.shared.windows.first!.rootViewController!
    GIDSignIn.sharedInstance()?.signIn()
    Loader.shared.start()
  }
  
  func signOut() {
    self.currentUser = nil
    UIApplication.shared.windows.first?.rootViewController = SignInViewController.userInstance
    GIDSignIn.sharedInstance()?.signOut()
  }
  
}

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
    if let auth = user.authentication {
      SignInManager.shared.token = auth.idToken
    }
    
    let user = User()
    user.name = name
    user.email = email
    user.imageUrl = imageUrl?.absoluteString
    SignInManager.shared.currentUser = user
    
    DispatchQueue.global(qos: .background).async {
      self.service.onboardUser(emailId: email ?? "", user: user) { (result) in
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
  
  func sign(_ signIn: GIDSignIn!, didDisconnectWith user: GIDGoogleUser!, withError error: Error!) {
    
  }
}
