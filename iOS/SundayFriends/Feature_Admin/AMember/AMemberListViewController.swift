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
  @IBOutlet weak var searchBar: UISearchBar!
  @IBOutlet weak var searchButton: UIButton!
  var offset: Int = 0
  let limit: Int = 25
  
  @IBAction func refreshBarButtonClicked(_ sender: Any) {
    self.searchBar.resignFirstResponder()
    self.searchBar.text = ""
    self.offset = 0
    self.members = []
    self.refresh()
  }
  
  
  @IBAction func searchClicked(_ sender: Any) {
    self.searchBar.resignFirstResponder()
    if let text = self.searchBar.text {
      self.offset = 0
      self.members = []
      self.refresh(searchText: text)
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.customizeSearch()
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
      self.adminService.fetchUsers(emailId: SignInManager.shared.currentUser?.email ?? "", searchQuery: searchText, offset: aOffset, limit: aLimit) { (result) in
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
            UIAlertController.showError(withMessage: "sf.error.failedToFetchUsers".localized, onViewController: self)
          }
        }
      }
    }
  }
  
  func customizeSearch() {
    self.searchButton.layer.cornerRadius = 10
  }
}

extension AMemberListViewController: UISearchBarDelegate {
  
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
  
  func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
    let text = self.searchBar.text ?? ""
    if indexPath.row == self.members.count - 1, text.isEmpty {
      self.refresh(loader: false)
    }
  }
  
  func scrollViewDidScroll(_ scrollView: UIScrollView) {
    self.searchBar.resignFirstResponder()
  }
}

extension AMemberListViewController: MemberCellDelegate {
  
  func didSelectWithdraw(_ indexPath: IndexPath?) {
    if let indexpath = indexPath, let userId = members[indexpath.row].userId {
      showAmountDialog(type: false, userId: userId)
    } else {
      UIAlertController.showError(withMessage: "sf.error.invalidUser".localized, onViewController: self)
    }
  }
  
  func didSelectDeposit(_ indexPath: IndexPath?) {
    if let indexpath = indexPath, let userId = members[indexpath.row].userId {
      showAmountDialog(type: true, userId: userId)
    } else {
      UIAlertController.showError(withMessage: "sf.error.invalidUser".localized, onViewController: self)
    }
  }
  
  func showAmountDialog(type: Bool, userId: Int) {
    let alertController = UIAlertController(title: "sf.message.enterAmount".localized, message: "", preferredStyle: .alert)
    alertController.addTextField { (textField) in
      textField.placeholder = "sf.message.enterAmount".localized
    }
    let saveAction = UIAlertAction(title: "sf.confirm".localized, style: .default, handler: { alert -> Void in
      let firstTextField = alertController.textFields![0] as UITextField
      if let text = firstTextField.text, let amount = Int(text) {
        self.callApi(type: type, userId: userId, amount: amount)
      } else {
        alertController.dismiss(animated: false) {
          UIAlertController.showError(withMessage: "sf.error.invalidAmount".localized, onViewController: self)
        }
      }
    })
    let cancelAction = UIAlertAction(title: "sf.cancel".localized, style: .cancel, handler: {
      (action : UIAlertAction!) -> Void in })
    
    alertController.addAction(saveAction)
    alertController.addAction(cancelAction)
    
    self.present(alertController, animated: true, completion: nil)
  }
  
  func callApi(type: Bool, userId: Int, amount: Int) {
    Loader.shared.start(onView: self.view)
    DispatchQueue.global(qos: .background).async {
      self.adminService.transact(emailId: SignInManager.shared.currentUser?.email ?? "", userId: userId, amount: amount, type: type) { (result) in
        DispatchQueue.main.async {
          Loader.shared.stop()
        }
        switch result {
        case .success(let isSuccess):
          if isSuccess {
            DispatchQueue.main.async {
              if !type {
                UIAlertController.showMessage(withTitle: "sf.success".localized,
                                              andMessage: String(format: "sf.withdrawl.success".localized, "\(amount)"),
                                              onViewController: self) {
                  self.searchBar.text = nil
                  self.offset = 0
                  self.members = []
                  self.refresh()
                }
              } else {
                UIAlertController.showMessage(withTitle: "sf.success".localized,
                                              andMessage: String(format: "sf.deposit.success".localized, "\(amount)"),
                                              onViewController: self) {
                  self.searchBar.text = nil
                  self.offset = 0
                  self.members = []
                  self.refresh()
                }
              }
            }
          } else {
            DispatchQueue.main.async {
              UIAlertController.showError(withMessage: "sf.error.sometingWentWrong".localized, onViewController: self)
            }
          }
        case .failure(_):
          DispatchQueue.main.async {
            UIAlertController.showError(withMessage: "sf.error.sometingWentWrong".localized, onViewController: self)
          }
        }
      }
    }
  }
  
}
