//
//  HomeViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class HomeViewController: UBaseViewController {
  
  @IBOutlet weak var profileImage: UIImageView!
  @IBOutlet weak var nameLabel: UILabel!
  @IBOutlet weak var greetingLabel: UILabel!
  @IBOutlet weak var dateLabel: UILabel!
  @IBOutlet weak var ticketBalanceLabel: UILabel!
  @IBOutlet weak var ticketCardView: UIView!
  @IBOutlet weak var transactionCardView: UIView!
  @IBOutlet weak var familyCardView: UIView!
  @IBOutlet weak var profileCardView: UIView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    configureProfileImage()
    configureName()
    configureGreeting()
    configureDate()
    configureTicketBalance()
    configureTicketCardView()
    configureTransactionCardView()
    configureFamilyCardView()
    configureProfileCardView()
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    self.navigationController?.navigationBar.isHidden = true
  }

}

extension HomeViewController {
  
  func configureProfileImage() {
    self.profileImage.layer.cornerRadius = self.profileImage.bounds.width / 2
    AppUtils.loadImage(withUrl: SignInManager.shared.currentUser?.imageUrl, onImageView: self.profileImage)
  }
  
  func configureName(){
    if let user = SignInManager.shared.currentUser, let name = user.name {
      self.nameLabel.text = String(format: "sf.message.hi".localized, name)
    }else {
      self.nameLabel.text = "sf.message.hi.generic".localized
    }
  }
  
  func configureGreeting() {
    self.greetingLabel.text = "sf.message.haveANiceDay".localized
  }
  
  func configureDate() {
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "EEEE, MMMM d"
    self.dateLabel.text = dateFormatter.string(from: Date())
  }
  
  func configureTicketBalance() {
    if let user = SignInManager.shared.currentUser, let balance = user.balance {
      self.ticketBalanceLabel.text = "\(balance)"
    } else {
      self.ticketBalanceLabel.text = nil
    }
  }
  
  func configureTicketCardView() {
    ShadowUtility.applyShadow(toView: self.ticketCardView, onSides: .allSides, shadowTraits: .defaultTraits)
  }
  
  func configureTransactionCardView() {
    ShadowUtility.applyShadow(toView: self.transactionCardView, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapTransactionCard))
    self.transactionCardView.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapTransactionCard() {
    if let user = SignInManager.shared.currentUser {
      let controller = TransactionsViewController.userInstance as! TransactionsViewController
      controller.userId = user.userId
      self.navigationController?.pushViewController(controller, animated: true)
    }
  }
  
  func configureFamilyCardView() {
    ShadowUtility.applyShadow(toView: self.familyCardView, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapFamilyCard))
    self.familyCardView.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapFamilyCard() {
    if let tabBarController = UIApplication.shared.windows.first?.rootViewController as? UITabBarController {
      tabBarController.selectedIndex = 1
    }
  }
  
  func configureProfileCardView() {
    ShadowUtility.applyShadow(toView: self.profileCardView, onSides: .allSides, shadowTraits: .defaultTraits)
    let tapGestuer: UITapGestureRecognizer = UITapGestureRecognizer.init(target: self, action: #selector(tapProfileCard))
    self.profileCardView.addGestureRecognizer(tapGestuer)
  }
  
  @objc func tapProfileCard() {
    if let tabBarController = UIApplication.shared.windows.first?.rootViewController as? UITabBarController {
      tabBarController.selectedIndex = 2
    }
  }
  
}
