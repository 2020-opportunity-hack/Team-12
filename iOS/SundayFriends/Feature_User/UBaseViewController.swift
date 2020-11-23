//
//  UBaseViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class UBaseViewController: UIViewController {
  let service: UserAPIInterface = AppUtils.MOCK_ENABLED ? UserAPIMock() : UserAPI()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.navigationController?.navigationBar.isHidden = false
  }
  
}
