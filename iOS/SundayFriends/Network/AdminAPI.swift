//
//  AdminAPI.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

//private var BASE_URL: String = "http://34.240.219.132:8080"
private var BASE_URL: String = "http://localhost:8080"

enum AdminAPIRequests: Requests {
  case fetchUsers(searchQuery: String?)
  case transact(userId: Int, amount: Int, type: Bool)
  case linkFamily(userId: Int, familyId: Int)
  case deactivateUser(userId: Int, deactivate: Bool)
  case fetchDeactivatedusers
  
  var url: URL? {
    switch self{
    case .fetchUsers(let searchQuery):
      if let searchQuery = searchQuery {
        return URL.init(string: "\(BASE_URL)/admin/fetchUsers?searchQuery=\(searchQuery)")
      }
      return URL.init(string: "\(BASE_URL)/admin/fetchUsers")
    case .transact(let userId, let amount, let type):
      return URL.init(string: "\(BASE_URL)/admin/transact?userId=\(userId)&amount=\(amount)&type=\(type ? 1 : 0)")
    case .linkFamily(let userId, let familyId):
      return URL.init(string: "\(BASE_URL)/admin/link_family?userId=\(userId)&familyId=\(familyId)")
    case .deactivateUser(let userId, let deactivate):
      return URL.init(string: "\(BASE_URL)/admin/deactivate_user?userId=\(userId)&deactivate=\(deactivate)")
    case .fetchDeactivatedusers:
      return URL.init(string: "\(BASE_URL)/admin/deactivatedUsers")
    }
  }
  
  var header: [String : String]?{
    switch self{
    case .fetchUsers: return nil
    case .transact(_,_,_): return nil
    case .linkFamily(_,_): return nil
    case .deactivateUser(_,_): return nil
    case .fetchDeactivatedusers: return nil
    }
  }
  
  var body: [String : AnyHashable]?{
    switch self{
    case .fetchUsers: return nil
    case .transact(_,_,_): return nil
    case .linkFamily(_,_): return nil
    case .deactivateUser(_,_): return nil
    case .fetchDeactivatedusers: return nil
    }
  }
}

protocol AdminAPIInterface {
  func fetchUsers(searchQuery: String?, completion: @escaping (Result<[User]>) -> ())
  func transact(userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ())
  func linkFamily(userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ())
  func deactivateUser(userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ())
  func fetchDeactivatedUsers(completion: @escaping (Result<[User]>) -> ())
}

class AdminAPI: AdminAPIInterface {
  let service: Service = NetworkService()
  
  func fetchUsers(searchQuery: String?, completion: @escaping (Result<[User]>) -> ()) {
    let request = AdminAPIRequests.fetchUsers(searchQuery: searchQuery)
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(let data):
        do {
          let userModel : [User] = try JSONDecoder().decode([User].self, from: data)
          completion(.success(userModel))
        } catch {
          completion(.failure(error))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func transact(userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.transact(userId: userId, amount: amount, type: type)
    service.post(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(_):
        if let code = statusCode, code == 200 {
          completion(.success(true))
        } else {
          completion(.success(false))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func linkFamily(userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.linkFamily(userId: userId, familyId: familyId)
    service.put(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(_):
        if let code = statusCode, code == 200 {
          completion(.success(true))
        } else {
          completion(.success(false))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func deactivateUser(userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.deactivateUser(userId: userId, deactivate: deactivate)
    service.put(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(_):
        if let code = statusCode, code == 200 {
          completion(.success(true))
        } else {
          completion(.success(false))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
  func fetchDeactivatedUsers(completion: @escaping (Result<[User]>) -> ()) {
    let request = AdminAPIRequests.fetchDeactivatedusers
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      switch result {
      case .success(let data):
        do {
          let userModel : [User] = try JSONDecoder().decode([User].self, from: data)
          completion(.success(userModel))
        } catch {
          completion(.failure(error))
        }
      case .failure(let error):
        completion(.failure(error))
      }
    }
  }
  
}


class AdminAPIMock: AdminAPIInterface {
  
  func fetchUsers(searchQuery: String?, completion: @escaping (Result<[User]>) -> ()) {
    if let path = Bundle.main.path(forResource: "admin_fetchUsers", ofType: "json") {
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
  
  func transact(userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ()) {
    if let path = Bundle.main.path(forResource: "admin_transact", ofType: "json") {
      do {
        let url = URL.init(fileURLWithPath: path)
        let data = try Data.init(contentsOf: url)
        let userModel : ResultModel = try JSONDecoder().decode(ResultModel.self, from: data)
        completion(.success(userModel.isSuccess))
      }catch{
        completion(.failure(error))
      }
    } else {
      completion(.failure(UserError.unableToParse))
    }
  }
  
  func linkFamily(userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ()) {
    if let path = Bundle.main.path(forResource: "admin_linkFamily", ofType: "json") {
      do {
        let url = URL.init(fileURLWithPath: path)
        let data = try Data.init(contentsOf: url)
        let userModel : ResultModel = try JSONDecoder().decode(ResultModel.self, from: data)
        completion(.success(userModel.isSuccess))
      }catch{
        completion(.failure(error))
      }
    } else {
      completion(.failure(UserError.unableToParse))
    }
  }
  
  func deactivateUser(userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ()) {
    completion(.failure(UserError.unableToParse))
  }
  
  func fetchDeactivatedUsers(completion: @escaping (Result<[User]>) -> ()) {
    completion(.failure(UserError.unableToParse))
  }
}
