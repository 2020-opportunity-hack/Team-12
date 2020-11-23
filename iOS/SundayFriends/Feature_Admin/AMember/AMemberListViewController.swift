//
//  MemberListViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class AMemberListViewController: ABaseViewController {
  var members: [User] = [User]()
  
  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    refresh()
  }
  
  func refresh() {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      self.adminService.fetchUsers { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let users):
          self.members = [User]()
          self.members.append(contentsOf: users)
          DispatchQueue.main.async {
            self.tableView.reloadData()
          }
        case .failure(_):
          DispatchQueue.main.async {
            UIAlertController.showError(withMessage: "Failed to fetch users", onViewController: self)
          }
        }
      }
    }
  }
  
}

extension AMemberListViewController : UITableViewDelegate, UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return members.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: MemberCell.self), for: indexPath) as? MemberCell else {
      return UITableViewCell()
    }
    cell.indexPath = indexPath
    cell.delegate = self
    let user = members[indexPath.row]
    cell.configure(name: user.name, email: user.email, imageUrl: user.imageUrl, balance: user.balance)
    return cell
  }
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 110
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let user = members[indexPath.row]
    let controller = TransactionsViewController.userInstance as! TransactionsViewController
    controller.userId = user.userId
    self.navigationController?.pushViewController(controller, animated: true)
  }
  
}

extension AMemberListViewController: MemberCellDelegate {
  
  func didSelectWithdraw(_ indexPath: IndexPath?) {
    if let indexpath = indexPath, let userId = members[indexpath.row].userId {
      showAmountDialog(type: false, userId: userId)
    } else {
      UIAlertController.showError(withMessage: "Invalid user", onViewController: self)
    }
  }
  
  func didSelectDeposit(_ indexPath: IndexPath?) {
    if let indexpath = indexPath, let userId = members[indexpath.row].userId {
      showAmountDialog(type: true, userId: userId)
    } else {
      UIAlertController.showError(withMessage: "Invalid user", onViewController: self)
    }
  }
  
  func showAmountDialog(type: Bool, userId: Int) {
    let alertController = UIAlertController(title: "Enter Amount", message: "", preferredStyle: .alert)
    alertController.addTextField { (textField) in
      textField.placeholder = "Enter Amount"
    }
    let saveAction = UIAlertAction(title: "Confirm", style: .default, handler: { alert -> Void in
      let firstTextField = alertController.textFields![0] as UITextField
      if let text = firstTextField.text, let amount = Int(text) {
        self.callApi(type: type, userId: userId, amount: amount)
      } else {
        alertController.dismiss(animated: false) {
          UIAlertController.showError(withMessage: "Invalid amount", onViewController: self)
        }
      }
    })
    let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: {
      (action : UIAlertAction!) -> Void in })
    
    alertController.addAction(saveAction)
    alertController.addAction(cancelAction)
    
    self.present(alertController, animated: true, completion: nil)
  }
  
  func callApi(type: Bool, userId: Int, amount: Int) {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      self.adminService.transact(userId: userId, amount: amount, type: type) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let isSuccess):
          if isSuccess {
            DispatchQueue.main.async {
              if !type {
                UIAlertController.showMessage(withTitle: "Success", andMessage: "\(amount) tickets withdrawn fron user's account.", onViewController: self) {
                  self.refresh()
                }
              } else {
                UIAlertController.showMessage(withTitle: "Success", andMessage: "\(amount) tickets deposited to user's account.", onViewController: self) {
                  self.refresh()
                }
              }
            }
          } else {
            DispatchQueue.main.async {
              UIAlertController.showError(withMessage: "Oops! something went wrong. Please try again!", onViewController: self)
            }
          }
        case .failure(_):
          DispatchQueue.main.async {
            UIAlertController.showError(withMessage: "Oops! something went wrong. Please try again!", onViewController: self)
          }
        }
      }
    }
  }
  
}
