//
//  AppUtils.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class AppUtils {
  
  static var MOCK_ENABLED: Bool = false
  
  static var THEME_COLOR = UIColor.init(red: 58/255, green: 130/255, blue: 58/255, alpha: 1.0)
  
  static var userScene: UITabBarController {
    return UIStoryboard.main.instantiateViewController(withIdentifier: "UserScene") as! UITabBarController
  }

  static var adminScene: UITabBarController {
    return UIStoryboard.admin.instantiateViewController(withIdentifier: "AdminScene") as! UITabBarController
  }
  
  static func loadImage(withUrl url: String?, onImageView imageView: UIImageView) {
    guard let url = url else { return }
    DispatchQueue.global(qos: .background).async {
      if let image_url =  URL.init(string: url) {
        do {
          let data = try Data.init(contentsOf: image_url)
          let image = UIImage.init(data: data, scale: 1.0)
          DispatchQueue.main.async {
            imageView.image = image
          }
        }catch{}
      }
    }
  }
  
}

extension UIStoryboard {
  static var main: UIStoryboard { return UIStoryboard.init(name: "Main", bundle: nil) }
  static var admin: UIStoryboard { return UIStoryboard.init(name: "Admin", bundle: nil) }
}

extension UIViewController {
  static var userInstance: UIViewController {
    return UIStoryboard.main.instantiateViewController(withIdentifier: String(describing: self))
  }
  
  static var adminInstance: UIViewController {
    return UIStoryboard.admin.instantiateViewController(withIdentifier: String(describing: self))
  }
}

