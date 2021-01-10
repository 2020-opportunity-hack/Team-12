//
//  AppDelegate.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 20/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit
import CoreData
import GoogleSignIn

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
  var window: UIWindow?
  
  func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
    SignInManager.shared.kickOff { (status) in
      switch status {
      case .notSignedIn(let message):
        let instance = SignInViewController.userInstance
        UIApplication.shared.windows.first?.rootViewController = instance
        if SignInManager.shared.isManualLogin {
          if let message = message {
            UIAlertController.showError(withMessage: message, onViewController: instance)
          } else {
            UIAlertController.showError(withMessage: "we were not able to log you in. Pelase try again!", onViewController: instance)
          }
        }
        break
      case .signedIn((let role)):
        switch role {
        case .user:
          if SignInManager.shared.isManualLogin {
            UIApplication.shared.windows.first?.rootViewController = AddFamilyViewController.userInstance
          } else {
            UIApplication.shared.windows.first?.rootViewController = AppUtils.userScene
          }
        case .admin:
          UIApplication.shared.windows.first?.rootViewController = AppUtils.adminScene
        }
        break
      }
    }
    return true
  }

  // MARK: - Core Data stack
  lazy var persistentContainer: NSPersistentContainer = {
      let container = NSPersistentContainer(name: "SundayFriends")
      container.loadPersistentStores(completionHandler: { (storeDescription, error) in
          if let error = error as NSError? {
              fatalError("Unresolved error \(error), \(error.userInfo)")
          }
      })
      return container
  }()

  // MARK: - Core Data Saving support
  func saveContext () {
      let context = persistentContainer.viewContext
      if context.hasChanges {
          do {
              try context.save()
          } catch {
              let nserror = error as NSError
              fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
          }
      }
  }

  func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
    return GIDSignIn.sharedInstance().handle(url)
  }
}
