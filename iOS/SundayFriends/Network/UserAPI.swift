//
//  UserAPI.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

private var BASE_URL: String = "http://34.240.219.132:8080"
//private var BASE_URL: String = "http://localhost:8080"

enum UserAPIRequests: Requests {
  case onboard(_ user: User?)
  case getFamilyList(_ familyId: Int,_ searchQuery: String?)
  case getTransactionsList(_ userId: Int)
  
  var url: URL? {
    switch self{
    case .onboard(let user):
      var urlString: String = "\(BASE_URL)/user/onboard?"
      if let name = user?.name, let email = user?.email {
        urlString.append("name=\(name)&email=\(email)")
      }
      if let imageurl = user?.imageUrl {
        urlString.append("&imageUrl=\(imageurl)")
      }
      if let familyId = user?.familyId {
        urlString.append("&familyId=\(familyId)")
      }
      return URL.init(string: urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)
    case .getFamilyList(let familyId, let searchQuery):
      if let searchQueery = searchQuery {
        return URL.init(string: "\(BASE_URL)/user/get_family?familyId=\(familyId)&searchQuery=\(searchQueery)")
      } else {
        return URL.init(string: "\(BASE_URL)/user/get_family?familyId=\(familyId)")
      }
    case .getTransactionsList(let userId):
      return URL.init(string: "\(BASE_URL)/user/transactions?userId=\(userId)")
    }
  }
  
  var header: [String : String]?{
    switch self{
    case .onboard(_): return nil
    case .getFamilyList(_,_): return nil
    case .getTransactionsList(_): return nil
    }
  }
  
  var body: [String : AnyHashable]?{
    switch self{
    case .onboard(_): return nil
    case .getFamilyList(_,_): return nil
    case .getTransactionsList(_): return nil
    }
  }
}

protocol UserAPIInterface {
  func onboardUser(user: User?, completion: @escaping (Result<User>) -> ())
  func getFamilyList(familyId: Int, searchQuery: String?, completion: @escaping (Result<[User]>) -> ())
  func getTransactionsList(userId: Int, completion: @escaping (Result<TransactionMap>) -> ())
}

class UserAPI: UserAPIInterface {
  
  let service: Service = NetworkService()
  
  func onboardUser(user: User?, completion: @escaping (Result<User>) -> ()) {
    let request = UserAPIRequests.onboard(user)
    service.post(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(let data):
        do {
          let userModel : User = try JSONDecoder().decode(User.self, from: data)
          completion(.success(userModel))
        } catch {
          completion(.failure(error))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func getFamilyList(familyId: Int, searchQuery: String?, completion: @escaping (Result<[User]>) -> ()) {
    let request = UserAPIRequests.getFamilyList(familyId, searchQuery)
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(let data):
        do {
          if data.count > 0 {
            let users : [User] = try JSONDecoder().decode([User].self, from: data)
            completion(.success(users))
          } else {
            completion(.failure(UserError.empty))
          }
        } catch {
          completion(.failure(error))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func getTransactionsList(userId: Int, completion: @escaping (Result<TransactionMap>) -> ()) {
    let request = UserAPIRequests.getTransactionsList(userId)
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(let data):
        do {
          if data.count > 0 {
            let transactions : TransactionMap = try JSONDecoder().decode(TransactionMap.self, from: data)
            completion(.success(transactions))
          } else {
            completion(.failure(UserError.empty))
          }
        } catch {
          completion(.failure(error))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
}


class UserAPIMock: UserAPIInterface {
  
  func onboardUser(user: User?, completion: @escaping (Result<User>) -> ()) {
    if let path = Bundle.main.path(forResource: "user_onboard", ofType: "json") {
      do {
        let url = URL.init(fileURLWithPath: path)
        let data = try Data.init(contentsOf: url)
        let userModel : User = try JSONDecoder().decode(User.self, from: data)
        completion(.success(userModel))
      }catch{
        completion(.failure(error))
      }
    } else {
      completion(.failure(UserError.unableToParse))
    }
  }
  
  func getFamilyList(familyId: Int, searchQuery: String?, completion: @escaping (Result<[User]>) -> ()) {
    if let path = Bundle.main.path(forResource: "user_getFamily", ofType: "json") {
      do {
        let url = URL.init(fileURLWithPath: path)
        let data = try Data.init(contentsOf: url)
        let userModel : [User] = try JSONDecoder().decode([User].self, from: data)
        completion(.success(userModel))
      }catch{
        completion(.failure(error))
      }
    } else {
      completion(.failure(UserError.unableToParse))
    }
  }
  
  func getTransactionsList(userId: Int, completion: @escaping (Result<TransactionMap>) -> ()) {
    if let path = Bundle.main.path(forResource: "user_getTransactions", ofType: "json") {
      do {
        let url = URL.init(fileURLWithPath: path)
        let data = try Data.init(contentsOf: url)
        let userModel : TransactionMap = try JSONDecoder().decode(TransactionMap.self, from: data)
        completion(.success(userModel))
      }catch{
        completion(.failure(error))
      }
    } else {
      completion(.failure(UserError.unableToParse))
    }
  }
  
}


enum UserError: Error {
  case unableToParse
  case empty
  
  var description : String{
    switch self {
    case .unableToParse: return "Failed to parse!"
    case .empty: return "Empty response!"
    }
  }
}

