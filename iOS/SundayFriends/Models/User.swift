//
//  User.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class User: Codable {
  var userId: Int?
  var name: String?
  var email: String?
  var familyId: Int?
  var admin: Bool = false
  var imageUrl: String?
  var address: String?
  var balance: Int?
  
  init() {}
}
