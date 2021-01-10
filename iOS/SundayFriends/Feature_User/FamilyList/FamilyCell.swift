//
//  FamilyCell.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class FamilyCell: UITableViewCell {
  
  @IBOutlet weak var memberImage: UIImageView!
  @IBOutlet weak var name: UILabel!
  @IBOutlet weak var balnce: UILabel!
  @IBOutlet weak var email: UILabel!
  
  override func awakeFromNib() {
    super.awakeFromNib()
    memberImage.layer.cornerRadius = memberImage.bounds.width / 2
  }
  
  func configureCell(withName name: String, email: String?, balance: String, imageUrl: String?) {
    self.name.text = name
    self.balnce.text = balance
    self.email.text = email
    AppUtils.loadImage(withUrl: imageUrl, onImageView: self.memberImage)
  }
  
  override func prepareForReuse() {
    if #available(iOS 13.0, *) {
      self.memberImage.image = UIImage.init(systemName: "person.circle.fill")
    } else {
      self.memberImage.image = nil
    }
  }
}
