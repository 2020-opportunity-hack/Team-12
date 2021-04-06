//
//  Ext_UIAlertController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

extension UIAlertController{
    
    class func showError(withMessage message:String, onViewController vc:UIViewController){
      let alertController = self.init(title: "sf.error".localized, message: message, preferredStyle: .alert)
      let alertAction = UIAlertAction.init(title: "sf.ok".localized, style: .cancel, handler: nil)
        alertController.addAction(alertAction)
        vc.present(alertController, animated: true, completion: nil)
    }
    
    class func showMessage(withTitle title: String, andMessage message:String, onViewController vc:UIViewController, okTappedCallback : @escaping ()-> ()){
        let alertController = UIAlertController.init(title: title, message: message, preferredStyle: .alert)
      let alertAction = UIAlertAction.init(title: "sf.ok".localized, style: .default) { (alertAction) in
            okTappedCallback()
        }
        alertController.addAction(alertAction)
        vc.present(alertController, animated: true, completion: nil)
    }
    
    class func showMessageWithOptions(withTitle title: String, andMessage message:String, onViewController vc:UIViewController, noTappedCallback : @escaping ()-> (), yesTappedCallback : @escaping ()-> ()){
        let alertController = UIAlertController.init(title: title, message: message, preferredStyle: .alert)
      let alertActionNo = UIAlertAction.init(title: "sf.no".localized, style: .default) { (alertAction) in
            noTappedCallback()
        }
      let alertActionYes = UIAlertAction.init(title: "sf.yes".localized, style: .default) { (alertAction) in
            yesTappedCallback()
        }
        alertController.addAction(alertActionNo)
        alertController.addAction(alertActionYes)
        vc.present(alertController, animated: true, completion: nil)
    }
    
}
