//
//  AHomeViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class AHomeViewController: ABaseViewController {
  
  @IBOutlet weak var memberImage: UIImageView!
  @IBOutlet weak var nameLabel: UILabel!
  @IBOutlet weak var greetingLabel: UILabel!
  @IBOutlet weak var dateLabel: UILabel!
  
  @IBOutlet weak var viewAllUsersCard: UIView!
  @IBOutlet weak var withdrawDepositCard: UIView!
  @IBOutlet weak var linkFamilyCard: UIView!
  @IBOutlet weak var activateDeactivateCard: UIView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    configureProfileImage()
    configureName()
    configureGreeting()
    configureDate()
    configureViewAllCardView()
    configureWithdrawDepositCardView()
    configureLinkfamilyCardView()
    configureActivateCard()
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.navigationController?.navigationBar.isHidden = true
  }
  
}

extension AHomeViewController {
  
  func configureProfileImage() {
    self.memberImage.layer.cornerRadius = self.memberImage.bounds.width / 2
    AppUtils.loadImage(withUrl: SignInManager.shared.currentUser?.imageUrl, onImageView: self.memberImage)
  }
  
  func configureName(){
    if let user = SignInManager.shared.currentUser, let name = user.name {
      self.nameLabel.text = "Hi, \(name)"
    }else {
      self.nameLabel.text = "Hi, There"
    }
  }
  
  func configureGreeting() {
    self.greetingLabel.text = "Have a nice day"
  }
  
  func configureDate() {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "EEEE, MMMM d"
    self.dateLabel.text = dateFormatter.string(from: Date())
  }
  
  func configureViewAllCardView() {
    ShadowUtility.applyShadow(toView: self.viewAllUsersCard, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapViewAllCard))
    self.viewAllUsersCard.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapViewAllCard() {
    if let tabBarController = UIApplication.shared.windows.first?.rootViewController as? UITabBarController {
      tabBarController.selectedIndex = 1
    }
  }
  
  func configureWithdrawDepositCardView() {
    ShadowUtility.applyShadow(toView: self.withdrawDepositCard, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapWithdrawDepositCard))
    self.withdrawDepositCard.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapWithdrawDepositCard() {
    if let tabBarController = UIApplication.shared.windows.first?.rootViewController as? UITabBarController {
      tabBarController.selectedIndex = 1
    }
  }
  
  func configureLinkfamilyCardView() {
    ShadowUtility.applyShadow(toView: self.linkFamilyCard, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapLinkfamilyCard))
    self.linkFamilyCard.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapLinkfamilyCard() {
    let linkFamily = LinkFamilyViewController.adminInstance as! LinkFamilyViewController
    linkFamily.flow = .linkFamily
    self.navigationController?.pushViewController(linkFamily, animated: true)
  }
  
  func configureActivateCard() {
    ShadowUtility.applyShadow(toView: self.activateDeactivateCard, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapActivateCard))
    self.activateDeactivateCard.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapActivateCard() {
    if let tabBarController = UIApplication.shared.windows.first?.rootViewController as? UITabBarController {
      tabBarController.selectedIndex = 2
    }
  }
}
