//
//  AddFamilyViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class AddFamilyViewController: UIViewController {
  let service: UserAPIInterface = AppUtils.MOCK_ENABLED ? UserAPIMock() : UserAPI()
  let adminService: AdminAPIInterface = AppUtils.MOCK_ENABLED ? AdminAPIMock() : AdminAPI()
  
  var familyId: String?
  
  @IBOutlet weak var familyIdTextField: UITextField!
  @IBOutlet weak var proceedButton: UIButton!
  @IBOutlet weak var skipButton: UIButton!
  
  
  @IBAction func proceedAction(_ sender: Any) {
    self.familyIdTextField.resignFirstResponder()
    proceedToHome()
  }
  
  @IBAction func skipAction(_ sender: Any) {
    self.familyIdTextField.resignFirstResponder()
    UIApplication.shared.windows.first?.rootViewController = AppUtils.userScene
  }
  
  func proceedToHome() {
    Loader.shared.start()
    if let id = self.familyId, let familyId = Int(id), let user = SignInManager.shared.currentUser, let userId = user.userId {
      DispatchQueue.global(qos: .background).async {
        self.adminService.linkFamily(userId: userId, familyId: familyId) { (result) in
          DispatchQueue.main.async {
            switch result {
            case .success(let isSuccess):
              if isSuccess {
                SignInManager.shared.currentUser?.familyId = familyId
                UIApplication.shared.windows.first?.rootViewController = AppUtils.userScene
              } else {
                UIAlertController.showError(withMessage: "Please enter a valid family id!", onViewController: self)
              }
            case .failure(_):
              UIAlertController.showError(withMessage: "Unable to add family", onViewController: self)
            }
            Loader.shared.stop()
          }
        }
      }
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    let gesture: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(gestureDone))
    self.view.addGestureRecognizer(gesture)
    NotificationCenter.default.addObserver(self, selector: #selector(AddFamilyViewController.keyboardWillShow), name: UIResponder.keyboardWillShowNotification, object: nil)
    NotificationCenter.default.addObserver(self, selector: #selector(AddFamilyViewController.keyboardWillHide), name: UIResponder.keyboardWillHideNotification, object: nil)
    
  }
  
  @objc func gestureDone(){
    self.familyIdTextField.resignFirstResponder()
  }
  
  @objc func keyboardWillShow(notification: NSNotification) {
    guard let keyboardSize = (notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue else {
      return
    }
    self.view.frame.origin.y = 0 - keyboardSize.height + 100
  }
  
  @objc func keyboardWillHide(notification: NSNotification) {
    self.view.frame.origin.y = 0
  }
  
}

extension AddFamilyViewController: UITextFieldDelegate {
  
  func textFieldDidEndEditing(_ textField: UITextField) {
    familyId = textField.text
  }
  
}
