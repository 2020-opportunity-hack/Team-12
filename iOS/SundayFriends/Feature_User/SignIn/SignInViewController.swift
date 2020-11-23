//
//  SignInViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class SignInViewController: UBaseViewController {
  
  @IBOutlet weak var signInButton: UIButton!
  
  @IBAction func tapSIgnIn(_ sender: Any) {
    SignInManager.shared.signIn()
  }
  
}
