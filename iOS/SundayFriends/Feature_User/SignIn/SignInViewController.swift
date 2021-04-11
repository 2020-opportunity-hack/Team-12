//
//  SignInViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import AuthenticationServices
import UIKit

class SignInViewController: UBaseViewController {
  
  @IBOutlet weak var signInButton: UIButton!
  
  @IBAction func tapSIgnIn(_ sender: Any) {
    SignInManager.shared.signIn()
  }
  
  @IBOutlet weak var appleSignInButton: ASAuthorizationAppleIDButton!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    appleSignInButton.addTarget(self, action: #selector(handleAuthorizationAppleIDButtonPress), for: .touchUpInside)
  }
  
  @objc func handleAuthorizationAppleIDButtonPress() {
    SignInManager.shared.signInWithApple()
  }
}
