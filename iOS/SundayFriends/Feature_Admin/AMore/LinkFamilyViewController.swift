//
//  LinkFamilyViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

enum LinkFamilyFlow {
  case linkFamily
  case deactivateUsers
  case activateUsers
}

class LinkFamilyViewController: ABaseViewController {
  var members: [User] = [User]()
  var flow: LinkFamilyFlow = .linkFamily
  
  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    refresh()
  }
  
  func refresh() {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      if self.flow == .activateUsers {
        self.adminService.fetchDeactivatedUsers { (result) in
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
      } else {
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
}

extension LinkFamilyViewController: UITableViewDelegate, UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return members.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: "LinkFamilyCell", for: indexPath)
    let user = members[indexPath.row]
    cell.textLabel?.text = user.name
    cell.detailTextLabel?.text = user.email
    return cell
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let user = members[indexPath.row]
    DispatchQueue.main.async {
      if let userId = user.userId, let name = user.name {
        if self.flow == .deactivateUsers {
          self.showDeactivateDialog(userId: userId, name: name)
        } else if self.flow == .activateUsers {
          self.showActivateDialog(userId: userId, name: name)
        } else {
          self.showAmountDialog(userId: userId, name: name)
        }
      } else {
        UIAlertController.showError(withMessage: "Invalid user", onViewController: self)
      }
    }
  }
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    60
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func showAmountDialog(userId: Int, name: String) {
    let alertController = UIAlertController(title: "Link Family", message: "Enter the family id you want to link \(name) to", preferredStyle: .alert)
    alertController.addTextField { (textField) in
      textField.placeholder = "Enter Family Id"
    }
    let saveAction = UIAlertAction(title: "Confirm", style: .default, handler: { alert -> Void in
      let firstTextField = alertController.textFields![0] as UITextField
      if let text = firstTextField.text, let familyId = Int(text) {
        self.callApi(userId: userId, familyId: familyId)
      } else {
        alertController.dismiss(animated: false) {
          UIAlertController.showError(withMessage: "Invalid Family Id", onViewController: self)
        }
      }
    })
    let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: {
      (action : UIAlertAction!) -> Void in })
    
    alertController.addAction(saveAction)
    alertController.addAction(cancelAction)
    
    self.present(alertController, animated: true, completion: nil)
  }
  
  func callApi(userId: Int, familyId: Int) {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      self.adminService.linkFamily(userId: userId, familyId: familyId) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let isSuccess):
          if isSuccess {
            DispatchQueue.main.async {
              UIAlertController.showMessage(withTitle: "Success!", andMessage: "Family Successfully linked!", onViewController: self, okTappedCallback: {
                self.navigationController?.popViewController(animated: true)
              })
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

// MARK: Deactivate user
extension LinkFamilyViewController {
  
  func showDeactivateDialog(userId: Int, name: String) {
    let alertController = UIAlertController(title: "Deactivation", message: "Are you sure you want to deactivate \(name)?", preferredStyle: .alert)
    let saveAction = UIAlertAction(title: "Confirm", style: .default, handler: { alert -> Void in
      self.callDeactivationApi(userId: userId, deactivate: true)
    })
    let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: {
      (action : UIAlertAction!) -> Void in })
    
    alertController.addAction(saveAction)
    alertController.addAction(cancelAction)
    
    self.present(alertController, animated: true, completion: nil)
  }
  
  func showActivateDialog(userId: Int, name: String) {
    let alertController = UIAlertController(title: "Activation", message: "Are you sure you want to activate \(name)?", preferredStyle: .alert)
    let saveAction = UIAlertAction(title: "Confirm", style: .default, handler: { alert -> Void in
      self.callDeactivationApi(userId: userId, deactivate: false)
    })
    let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: {
      (action : UIAlertAction!) -> Void in })
    
    alertController.addAction(saveAction)
    alertController.addAction(cancelAction)
    
    self.present(alertController, animated: true, completion: nil)
  }
  
  func callDeactivationApi(userId: Int, deactivate: Bool) {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      self.adminService.deactivateUser(userId: userId, deactivate: deactivate) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let isSuccess):
          if isSuccess {
            DispatchQueue.main.async {
              if deactivate {
                UIAlertController.showMessage(withTitle: "Success!", andMessage: "User deactivated!", onViewController: self, okTappedCallback: {
                  self.refresh()
                })
              } else {
                UIAlertController.showMessage(withTitle: "Success!", andMessage: "User activated!", onViewController: self, okTappedCallback: {
                  self.refresh()
                })
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