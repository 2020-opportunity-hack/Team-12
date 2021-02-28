//
//  AdminAPI.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

private var BASE_URL: String = "http://184.169.189.74:8080"
//private var BASE_URL: String = "http://localhost:8080"

enum AdminAPIRequests: Requests {
  case fetchUsers(searchQuery: String?, emailId: String, offset: Int?, limit: Int?)
  case transact(userId: Int, amount: Int, type: Bool, emailId: String)
  case linkFamily(userId: Int, familyId: Int, emailId: String)
  case deactivateUser(userId: Int, deactivate: Bool, emailId: String)
  case fetchDeactivatedusers(searchQuery: String?, emailId: String, offset: Int?, limit: Int?)
  
  var url: URL? {
    switch self{
    case .fetchUsers(let searchQuery, _, let offset, let limit):
      var url: String = "\(BASE_URL)/admin/fetchUsers"
      if let searchQuery = searchQuery {
        url += "?searchQuery=\(searchQuery)"
      }
      if let offset = offset, let limit = limit {
        if url.contains("?") {
          url += "&offset=\(offset)&limit=\(limit)"
        } else {
          url += "?offset=\(offset)&limit=\(limit)"
        }
      }
      return URL.init(string: url)
    case .transact(let userId, let amount, let type, _):
      return URL.init(string: "\(BASE_URL)/admin/transact?userId=\(userId)&amount=\(amount)&type=\(type ? 1 : 0)")
    case .linkFamily(let userId, let familyId, _):
      return URL.init(string: "\(BASE_URL)/admin/link_family?userId=\(userId)&familyId=\(familyId)")
    case .deactivateUser(let userId, let deactivate, _):
      return URL.init(string: "\(BASE_URL)/admin/deactivate_user?userId=\(userId)&deactivate=\(deactivate)")
    case .fetchDeactivatedusers(let searchQuery, _, let offset, let limit):
      var url:String = "\(BASE_URL)/admin/deactivatedUsers"
      if let searchQuery = searchQuery {
        url += "?searchQuery=\(searchQuery)"
      }
      if let offset = offset, let limit = limit {
        if url.contains("?") {
          url += "&offset=\(offset)&limit=\(limit)"
        } else {
          url += "?offset=\(offset)&limit=\(limit)"
        }
      }
      return URL.init(string: url)
    }
  }
  
  var header: [String : String]?{
    switch self{
    case .fetchUsers(_, let email,_,_),
         .transact(_,_,_, let email),
         .linkFamily(_,_, let email),
         .deactivateUser(_,_, let email),
         .fetchDeactivatedusers(_,let email,_,_):
      
      return ["idToken": SignInManager.shared.token,
              "idClient": CLIENT_ID,
              "idEmail": email]
    }
  }
  
  var body: [String : AnyHashable]?{
    switch self{
    case .fetchUsers(_,_,_,_): return nil
    case .transact(_,_,_,_): return nil
    case .linkFamily(_,_,_): return nil
    case .deactivateUser(_,_,_): return nil
    case .fetchDeactivatedusers(_,_,_,_): return nil
    }
  }
}

protocol AdminAPIInterface {
  func fetchUsers(emailId: String, searchQuery: String?, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ())
  func transact(emailId: String, userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ())
  func linkFamily(emailId: String, userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ())
  func deactivateUser(emailId: String, userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ())
  func fetchDeactivatedUsers(searchQuery: String?, emailId: String, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ())
}

class AdminAPI: AdminAPIInterface {
  let service: Service = NetworkService()
  
  func fetchUsers(emailId: String, searchQuery: String?, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ()) {
    let request = AdminAPIRequests.fetchUsers(searchQuery: searchQuery, emailId: emailId, offset: offset, limit: limit)
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      if statusCode == 401 {
        completion(.failure(UserError.authError))
        return
      }
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
  
  func transact(emailId: String, userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.transact(userId: userId, amount: amount, type: type, emailId: emailId)
    service.post(request: request, session: URLSession.shared) { (result, statusCode) in
      if statusCode == 401 {
        completion(.failure(UserError.authError))
        return
      }
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
  
  func linkFamily(emailId: String, userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.linkFamily(userId: userId, familyId: familyId, emailId: emailId)
    service.put(request: request, session: URLSession.shared) { (result, statusCode) in
      if statusCode == 401 {
        completion(.failure(UserError.authError))
        return
      }
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
  
  func deactivateUser(emailId: String, userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ()) {
    let request = AdminAPIRequests.deactivateUser(userId: userId, deactivate: deactivate, emailId: emailId)
    service.put(request: request, session: URLSession.shared) { (result, statusCode) in
      if statusCode == 401 {
        completion(.failure(UserError.authError))
        return
      }
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
  
  func fetchDeactivatedUsers(searchQuery: String?, emailId: String, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ()) {
    let request = AdminAPIRequests.fetchDeactivatedusers(searchQuery: searchQuery, emailId: emailId, offset: offset, limit: limit)
    service.get(request: request, session: URLSession.shared) { (result, statusCode) in
      if statusCode == 401 {
        completion(.failure(UserError.authError))
        return
      }
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
  
  func fetchUsers(emailId: String, searchQuery: String?, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ()) {
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
  
  func transact(emailId: String, userId: Int, amount: Int, type: Bool, completion: @escaping (Result<Bool>) -> ()) {
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
  
  func linkFamily(emailId: String, userId: Int, familyId: Int, completion: @escaping (Result<Bool>) -> ()) {
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
  
  func deactivateUser(emailId: String, userId: Int, deactivate: Bool, completion: @escaping (Result<Bool>) -> ()) {
    completion(.failure(UserError.unableToParse))
  }
  
  func fetchDeactivatedUsers(searchQuery: String?, emailId: String, offset: Int?, limit: Int?, completion: @escaping (Result<[User]>) -> ()) {
    completion(.failure(UserError.unableToParse))
  }
}
