//
//  NetworkService.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 22/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//
import UIKit
import Foundation

//MARK: Result enum
enum Result<Value> {
  case success(Value)
  case failure(Error)
}

//MARK: Requests protocol
protocol Requests {
  var url: URL? { get }
  var header : [String : String]? { get }
  var body : [String : AnyHashable]? { get }
}

//MARK: Service protocol
protocol Service {
  func get(request: Requests, session: URLSession, completion: @escaping (Result<Data>, Int?) -> Void)
  func post(request: Requests, session: URLSession, completion: @escaping (Result<Data>,Int?) -> Void)
  func put(request: Requests, session: URLSession, completion: @escaping (Result<Data>, Int?) -> Void)
}

//MARK: Networking Service
final class NetworkService: Service {
  
  func get(request: Requests, session: URLSession = URLSession.shared, completion: @escaping (Result<Data>, Int?) -> Void) {
    if let url = request.url{
      var req : URLRequest = URLRequest.init(url: url)
      req.httpMethod = "GET"
      if let header = request.header{
        for (key,value) in header{
          req.setValue(value, forHTTPHeaderField: key)
        }
      }
      session.dataTask(with: req) { (data, response, error) in
        let statusCode = (response as? HTTPURLResponse)?.statusCode
        if let error = error {
          completion(.failure(error),statusCode)
          return
        }
        guard let data = data else {
          completion(.failure(ServiceError.invalidData),statusCode)
          return
        }
        completion(.success(data),statusCode)
      }.resume()
    }else{
      completion(.failure(ServiceError.invalidUrl), nil)
    }
  }
  
  func post(request: Requests, session: URLSession, completion: @escaping (Result<Data>, Int?) -> Void) {
    if let url = request.url{
      var req : URLRequest = URLRequest.init(url: url)
      req.httpMethod = "POST"
      if let header = request.header{
        for (key,value) in header{
          req.setValue(value, forHTTPHeaderField: key)
        }
      }
      
      if let parameters: [AnyHashable: Any] = request.body {
        do {
          req.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)
        } catch {
          completion(.failure(ServiceError.invalidData), nil)
          return
        }
      }
      session.dataTask(with: req) { (data, response, error) in
        let statusCode = (response as? HTTPURLResponse)?.statusCode
        if let error = error {
          completion(.failure(error), statusCode)
          return
        }
        guard let data = data else {
          completion(.failure(ServiceError.invalidData), statusCode)
          return
        }
        completion(.success(data), statusCode)
      }.resume()
    }else{
      completion(.failure(ServiceError.invalidUrl), nil)
    }
  }
  
  func put(request: Requests, session: URLSession, completion: @escaping (Result<Data>, Int?) -> Void) {
    if let url = request.url{
      var req : URLRequest = URLRequest.init(url: url)
      req.httpMethod = "PUT"
      if let header = request.header{
        for (key,value) in header{
          req.setValue(value, forHTTPHeaderField: key)
        }
      }
      
      if let parameters: [AnyHashable: Any] = request.body {
        do {
          req.httpBody = try JSONSerialization.data(withJSONObject: parameters, options: .prettyPrinted)
        } catch {
          completion(.failure(ServiceError.invalidData), nil)
          return
        }
      }
      session.dataTask(with: req) { (data, response, error) in
        let statusCode = (response as? HTTPURLResponse)?.statusCode
        if let error = error {
          completion(.failure(error), statusCode)
          return
        }
        guard let data = data else {
          completion(.failure(ServiceError.invalidData), statusCode)
          return
        }
        completion(.success(data), statusCode)
      }.resume()
    }else{
      completion(.failure(ServiceError.invalidUrl), nil)
    }
  }
}

//MARK: Service error
enum ServiceError: Error {
  case invalidUrl
  case invalidData
  
  var description : String{
    switch self {
    case .invalidUrl:
      return "sf.error.invalid.url".localized
    case .invalidData:
      return "sf.error.invalid.url".localized
    }
  }
}
