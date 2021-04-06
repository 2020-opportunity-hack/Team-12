//
//  Ext_String.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 14/03/21.
//  Copyright © 2021 Abhinav Roy. All rights reserved.
//

import Foundation

extension String {
  
  var localized: String {
    get { return NSLocalizedString(self, comment: "") }
  }
  
}
