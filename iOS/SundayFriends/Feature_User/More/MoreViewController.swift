//
//  MoreViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class MoreViewController: UBaseViewController {
  
  @IBOutlet weak var headerView: UIView!
  @IBOutlet weak var profileImage: UIImageView!
  @IBOutlet weak var name: UILabel!
  @IBOutlet weak var email: UILabel!
  
  @IBOutlet weak var tableView: UITableView!
  var profileDS: [(header: String, value: String)] = [(header: String, value: String)]()
  
  @IBOutlet weak var signOutButton: UIButton!
  
  @IBAction func signOutAction(_ sender: Any) {
    SignInManager.shared.signOut()
  }
  
  
  override func viewDidLoad() {
    super.viewDidLoad()
    configureProfileImage()
    configureName()
    configureEmail()
    
    let gradient = CAGradientLayer()
    gradient.frame = headerView.bounds
    gradient.colors = [UIColor.init(red: 58/255, green: 130/255, blue: 58/255, alpha: 0.2).cgColor,
                       AppUtils.THEME_COLOR.cgColor]
    headerView.layer.insertSublayer(gradient, at: 0)
    
    if let user = SignInManager.shared.currentUser {
      if let name = user.name {
        profileDS.append(("Name",name))
      }
      
      if let email = user.email {
        profileDS.append(("Email", email))
      }
      
      profileDS.append(("Phone", ""))
      
      if let address = user.address {
        profileDS.append(("Address", address))
      }
    }
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.navigationController?.navigationBar.isHidden = true
  }
  
}

extension MoreViewController {
  
  func configureProfileImage() {
    self.profileImage.layer.cornerRadius = self.profileImage.bounds.width / 2
    if let user = SignInManager.shared.currentUser, let image = user.imageUrl {
      AppUtils.loadImage(withUrl: image, onImageView: self.profileImage)
    }
  }
  
  func configureName() {
    self.name.textColor = UIColor.white
    if let user = SignInManager.shared.currentUser, let name = user.name {
      self.name.text = name
    }
  }
  
  func configureEmail() {
    self.email.textColor = UIColor.white
    if let user = SignInManager.shared.currentUser, let email = user.email {
      self.email.text = email
    }
  }
  
}

extension MoreViewController: UITableViewDataSource, UITableViewDelegate{
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 60
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return profileDS.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: MoreCell.self), for: indexPath) as? MoreCell else {
      return UITableViewCell()
    }
    let current = profileDS[indexPath.row]
    cell.nameLabel.text = current.header
    cell.detailLabel.text = current.value
    return cell
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
}
