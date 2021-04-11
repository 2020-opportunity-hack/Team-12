//
//  AMoreViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class AMoreViewController: ABaseViewController {
  
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
  
  @IBAction func editProfileAction(_ sender: Any) {
    // Hidden in the case of Admin
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    configureProfileImage()
    configureName()
    configureEmail()
    
    DispatchQueue.main.async {
      let gradient = CAGradientLayer()
      gradient.frame = self.headerView.bounds
      gradient.colors = [UIColor.init(red: 58/255, green: 130/255, blue: 58/255, alpha: 0.2).cgColor,
                         AppUtils.THEME_COLOR.cgColor]
      self.headerView.layer.insertSublayer(gradient, at: 0)
    }
    
    profileDS.append(("sf.admin.linkFamily".localized,""))
    profileDS.append(("sf.admin.deactivateAUser".localized, ""))
    profileDS.append(("sf.admin.activateAUser".localized, ""))
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.navigationController?.navigationBar.isHidden = true
  }
  
}

extension AMoreViewController {
  
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

extension AMoreViewController: UITableViewDataSource, UITableViewDelegate{
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 60
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return profileDS.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: AMoreCell.self), for: indexPath) as? AMoreCell else {
      return UITableViewCell()
    }
    let current = profileDS[indexPath.row]
    cell.name.text = current.header
    cell.deatail.text = current.value
    return cell
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    switch indexPath.row {
    case 0: // Link Family
      let linkFamily = LinkFamilyViewController.adminInstance as! LinkFamilyViewController
      linkFamily.flow = .linkFamily
      self.navigationController?.pushViewController(linkFamily, animated: true)
    case 1: // Deactivate user
      let linkFamily = LinkFamilyViewController.adminInstance as! LinkFamilyViewController
      linkFamily.flow = .deactivateUsers
      self.navigationController?.pushViewController(linkFamily, animated: true)
    case 2: // Activate user
      let linkFamily = LinkFamilyViewController.adminInstance as! LinkFamilyViewController
      linkFamily.flow = .activateUsers
      self.navigationController?.pushViewController(linkFamily, animated: true)
    default: break
    }
  }
  
}
