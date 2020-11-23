//
//  MemberCell.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

protocol MemberCellDelegate: class {
  func didSelectWithdraw(_ indexPath: IndexPath?)
  func didSelectDeposit(_ indexPath: IndexPath?)
}

class MemberCell: UITableViewCell {
  
  weak var delegate: MemberCellDelegate?
  var indexPath: IndexPath?
  @IBOutlet weak var memberImage: UIImageView!
  @IBOutlet weak var memberName: UILabel!
  @IBOutlet weak var memberEMail: UILabel!
  @IBOutlet weak var balanceLabel: UILabel!
  
  @IBOutlet weak var withdrawButton: UIButton!
  @IBOutlet weak var depositButton: UIButton!
  
  @IBAction func withdrawAction(_ sender: Any) {
    self.delegate?.didSelectWithdraw(self.indexPath)
  }
  
  @IBAction func depositAction(_ sender: Any) {
    self.delegate?.didSelectDeposit(self.indexPath)
  }

  func configure(name: String?, email: String?, imageUrl: String?, balance: Int?) {
    if let name = name {
      self.memberName.text = name
    }
    
    if let email = email {
      self.memberEMail.text = email
    }
    
    if let balance = balance {
      self.balanceLabel.text = "\(balance)"
    }
    
    AppUtils.loadImage(withUrl: imageUrl, onImageView: self.memberImage)
  }
  
  override func awakeFromNib() {
    super.awakeFromNib()
    self.memberImage.layer.cornerRadius = self.memberImage.bounds.width / 2
    self.withdrawButton.layer.cornerRadius = 10
    self.depositButton.layer.cornerRadius = 10
  }
  
  override func prepareForReuse() {
    if #available(iOS 13.0, *) {
      self.memberImage.image = UIImage.init(systemName: "person.circle.fill")
    } else {
      // Fallback on earlier versions
    }
  }
  
}
