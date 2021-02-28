//
//  Transaction.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class TransactionMap: Codable {
  var userId: Int?
  var transactions: [Transaction]?
  
  init() {}

}

class Transaction: Codable {
  
  var type: Int?
  var amount: Double?
  var balanceAfterAction: Double?
  var time: String?
  
  init() {}
  
}
