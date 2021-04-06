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
  
  func configure(type: Int, date: String, amount: Double) {
    if type == 0 {
      self.type.text = "sf.withdrawl".localized
    } else if type == 1 {
      self.type.text = "sf.deposit".localized
    } else if type == 2 {
      self.type.text = "sf.interest".localized
    }
    
    self.date.text = date
    
    if type == 0 {
      self.amount.text = "- \(amount)"
      self.amount.textColor = UIColor.red
    } else {
      self.amount.text = "+ \(amount)"
      self.amount.textColor = AppUtils.THEME_COLOR
    }
  }
}
