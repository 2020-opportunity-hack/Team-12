//
//  ABaseViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class ABaseViewController: UIViewController {
  var adminService: AdminAPIInterface = AppUtils.MOCK_ENABLED ? AdminAPIMock() : AdminAPI()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.navigationController?.navigationBar.isHidden = false
  }
  
}
