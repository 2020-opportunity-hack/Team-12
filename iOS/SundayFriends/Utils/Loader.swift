//
//  Loader.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class Loader: NSObject {
  
  public static let shared : Loader = Loader()
  var loaderView : UIView?
  
  public func start(){
    
    if let appWindow = UIApplication.shared.windows.first{
      loaderView = UIView.init(frame: appWindow.bounds)
      if let loaderView = loaderView {
        loaderView.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        let activityIndicator : UIActivityIndicatorView = UIActivityIndicatorView.init(style: .whiteLarge)
        activityIndicator.color = UIColor.white
        activityIndicator.center = loaderView.center
        DispatchQueue.main.async {
          activityIndicator.startAnimating()
          loaderView.addSubview(activityIndicator)
          appWindow.addSubview(loaderView)
        }
      }
    }
  }
  
  public func start(onView view: UIView){
    
    loaderView = UIView.init(frame: view.bounds)
    if let loaderView = loaderView {
      loaderView.backgroundColor = UIColor.black.withAlphaComponent(0.6)
      let activityIndicator : UIActivityIndicatorView = UIActivityIndicatorView.init(style: .whiteLarge)
      activityIndicator.color = UIColor.white
      activityIndicator.center = loaderView.center
      DispatchQueue.main.async {
        activityIndicator.startAnimating()
        loaderView.addSubview(activityIndicator)
        view.addSubview(loaderView)
      }
    }
  }
  
  public func stop(){
    if let loaderView = loaderView{
      DispatchQueue.main.async {
        loaderView.removeFromSuperview()
      }
    }
  }
}
