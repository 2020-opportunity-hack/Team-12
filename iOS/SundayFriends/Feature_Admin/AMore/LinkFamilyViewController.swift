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
  var offset: Int = 0
  var limit: Int = 5
  
  @IBOutlet weak var tableView: UITableView!
  
  @IBOutlet weak var searchBar: UISearchBar!
  @IBOutlet weak var searchButton: UIButton!
  
  @IBAction func searchAction(_ sender: Any) {
    self.searchBar.resignFirstResponder()
    if let text = self.searchBar.text {
      self.offset = 0
      self.members = []
      self.refresh(searchText: text)
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.searchButton.layer.cornerRadius = 10
    refresh()
  }
  
  func refresh(searchText: String? = nil, loader: Bool = true) {
    if loader { Loader.shared.start(onView: self.view) }
    var aOffset: Int?
    var aLimit: Int?
    let text = searchText ?? ""
    if text.isEmpty {
      aOffset = self.offset
      aLimit = self.limit
    }
    
    DispatchQueue.global(qos: .background).async {
      if self.flow == .activateUsers {
        self.adminService.fetchDeactivatedUsers(searchQuery: searchText,
                                                emailId: SignInManager.shared.currentUser?.email ?? "",
                                                offset: aOffset,
                                                limit: aLimit) { (result) in
          self.executeResultClosure(withResult: result, text: text, loader: loader)
        }
      } else {
        self.adminService.fetchUsers(emailId: SignInManager.shared.currentUser?.email ?? "",
                                     searchQuery: searchText,
                                     offset: aOffset,
                                     limit: aLimit) { (result) in
          self.executeResultClosure(withResult: result, text: text, loader: loader)
        }
      }
    }
  }
  
  private func executeResultClosure(withResult result: Result<[User]>, text: String, loader: Bool) {
    DispatchQueue.main.async {
      if loader { Loader.shared.stop() }
    }
    switch result {
    case .success(let users):
      self.members.append(contentsOf: users)
      if text.isEmpty {
        self.offset += self.limit
        if users.count > 0 {
          DispatchQueue.main.async { self.tableView.reloadData() }
        }
      } else {
        DispatchQueue.main.async { self.tableView.reloadData() }
      }
    case .failure(_):
      DispatchQueue.main.async {
        UIAlertController.showError(withMessage: "Failed to fetch users", onViewController: self)
      }
    }
  }
}

extension LinkFamilyViewController: UISearchBarDelegate {
  
  func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
    self.searchBar.resignFirstResponder()
    if let text = self.searchBar.text {
      self.offset = 0
      self.members = []
      self.refresh(searchText: text)
    }
  }
  
  func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
    if searchText.isEmpty {
      if let text = self.searchBar.text {
        self.offset = 0
        self.members = []
        self.refresh(searchText: text)
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
  
  func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
    let text = self.searchBar.text ?? ""
    if indexPath.row == self.members.count - 1, text.isEmpty {
      self.refresh(loader: false)
    }
  }
  
  func scrollViewDidScroll(_ scrollView: UIScrollView) {
    self.searchBar.resignFirstResponder()
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
      self.adminService.linkFamily(emailId: SignInManager.shared.currentUser?.email ?? "", userId: userId, familyId: familyId) { (result) in
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
      self.adminService.deactivateUser(emailId: SignInManager.shared.currentUser?.email ?? "", userId: userId, deactivate: deactivate) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let isSuccess):
          if isSuccess {
            DispatchQueue.main.async {
              if deactivate {
                UIAlertController.showMessage(withTitle: "Success!", andMessage: "User deactivated!", onViewController: self, okTappedCallback: {
                  self.searchBar.text = nil
                  self.offset = 0
                  self.members = []
                  self.refresh()
                })
              } else {
                UIAlertController.showMessage(withTitle: "Success!", andMessage: "User activated!", onViewController: self, okTappedCallback: {
                  self.searchBar.text = nil
                  self.offset = 0
                  self.members = []
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
