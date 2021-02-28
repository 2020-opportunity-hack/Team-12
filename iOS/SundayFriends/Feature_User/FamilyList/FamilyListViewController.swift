//
//  FamilyListViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class FamilyListViewController: UBaseViewController {
  
  @IBOutlet weak var tableView: UITableView!
  var familyList: [User] = [User]()
  @IBOutlet weak var searchButton: UIButton!
  @IBOutlet weak var searchBar: UISearchBar!
  
  @IBAction func refreshBarButtonClicked(_ sender: Any) {
    self.searchBar.resignFirstResponder()
    self.searchBar.text = ""
    self.refresh()
  }
  
  @IBAction func searchAction(_ sender: Any) {
    self.searchBar.resignFirstResponder()
    if let text = self.searchBar.text {
      self.refresh(searchText: text)
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.customizeSearch()
    self.refresh()
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.searchBar.resignFirstResponder()
  }
  
  func refresh(searchText: String? = nil){
    Loader.shared.start(onView: self.view)
    if let user = SignInManager.shared.currentUser, let familyId = user.familyId {
     self.familyList = [User]()
      //Append self
      //self.familyList.append(user)
      
//      //Append dummy user
//      let user1 = User()
//      user1.userId = 3
//      user1.name = "John Doe"
//      user1.email = "john.doe@gmail.com"
//      user1.familyId = 1005
//      user1.balance = 330
//      user1.active = true
//      user1.imageUrl = "https://lh3.googleusercontent.com/proxy/kSXvR3mECaHiz5nYJ4suJ-T_dq02jVZjHb3kFqmVxqpwpQjtD_QHGLi9kDMX6tgGHrEi6SPjLL6pBVJ9UIVHQGIOv5h8DP03ybb2-loddf7Y2M5ycGawI9c"
//      self.familyList.append(user1)
      
      DispatchQueue.global(qos: .background).async {
        self.service.getFamilyList(emailId: SignInManager.shared.currentUser?.email ?? "", familyId: familyId, searchQuery: searchText) { (result) in
          switch result {
          case .success(let users):
            
            //Append all family members execept self
            //let familyExceptUser = users.filter{ $0.userId != user.userId }
            self.familyList.append(contentsOf: users)
          case .failure(let error):
            DispatchQueue.main.async {
              if error.localizedDescription == UserError.empty.localizedDescription{
                  UIAlertController.showError(withMessage: "No Family members found", onViewController: self)
              } else {
                UIAlertController.showError(withMessage: "Something went wrong", onViewController: self)
              }
            }
          }
          DispatchQueue.main.async {
            Loader.shared.stop()
            self.tableView.reloadData()
          }
        }
      }
    }
  }
  
  func customizeSearch() {
    self.searchButton.layer.cornerRadius = 10
  }
  
}

extension FamilyListViewController: UISearchBarDelegate {
  
  func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
    self.searchBar.resignFirstResponder()
    if let text = self.searchBar.text {
      self.refresh(searchText: text)
    }
  }
  
  func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
    if searchText.isEmpty {
      if let text = self.searchBar.text {
        self.refresh(searchText: text)
      }
    }
  }
  
}

extension FamilyListViewController: UITableViewDataSource, UITableViewDelegate {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return self.familyList.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: FamilyCell.self), for: indexPath) as? FamilyCell else {
      return UITableViewCell()
    }
    let user = familyList[indexPath.row]
    cell.configureCell(withName: user.name ?? "Member name",
                       email: user.email ?? "mmeber@gmail.com",
                       balance: "\(user.balance ?? 0)",
      imageUrl: user.imageUrl ?? "")
    return cell
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let user = familyList[indexPath.row]
    let controller = TransactionsViewController.userInstance as! TransactionsViewController
    controller.userId = user.userId
    self.navigationController?.pushViewController(controller, animated: true)
  }
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 80.0
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func scrollViewDidScroll(_ scrollView: UIScrollView) {
    self.searchBar.resignFirstResponder()
  }
}
