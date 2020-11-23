//
//  TransactionCell.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class TransactionCell: UITableViewCell {
  
  @IBOutlet weak var type: UILabel!
  @IBOutlet weak var date: UILabel!
  @IBOutlet weak var amount: UILabel!
  
  override func awakeFromNib() {
    super.awakeFromNib()
  }
  
  func configure(isWithdrawl flag: Bool, date: String, amount: Int) {
    if !flag {
      self.type.text = "Withdrawl"
    } else {
      self.type.text = "Deposit"
    }
    
    self.date.text = date
    
    if !flag {
      self.amount.text = "- \(amount)"
      self.amount.textColor = UIColor.red
    } else {
      self.amount.text = "+ \(amount)"
      self.amount.textColor = AppUtils.THEME_COLOR
    }
  }
}
