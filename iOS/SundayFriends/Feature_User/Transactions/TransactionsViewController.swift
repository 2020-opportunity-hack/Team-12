//
//  TransactionsViewController.swift
//  SundayFriends
//
//  Created by Roy, Abhinav on 21/11/20.
//  Copyright © 2020 Abhinav Roy. All rights reserved.
//

import UIKit

class TransactionsViewController: UBaseViewController {
  
  var transactions: [Transaction] = [Transaction]()
  private var currentOffset: Int = 0
  private var limit: Int = 20
  public var userId: Int?
  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.fetchData()
  }
  
  private func fetchData(loader: Bool = true) {
    if loader { Loader.shared.start(onView: self.view) } 
    if let userId = self.userId {
      DispatchQueue.global(qos: .background).async {
        self.service.getTransactionsList(emailId: SignInManager.shared.currentUser?.email ?? "", userId: userId, offset: self.currentOffset, limit: self.limit) { (result) in
          switch result {
          case .success(let trans):
            if let transList = trans.transactions, !transList.isEmpty {
              self.transactions.append(contentsOf: transList)
              self.currentOffset += self.limit
              DispatchQueue.main.async {
                self.tableView.reloadData()
              }
            }
            break
          case .failure(let error):
            DispatchQueue.main.async {
              if error.localizedDescription == UserError.empty.localizedDescription {
                UIAlertController.showError(withMessage: "No transactions found for this user!", onViewController: self)
              } else {
                UIAlertController.showError(withMessage: "Unable to load transactions for this user!", onViewController: self)
              }
            }
            break
          }
          DispatchQueue.main.async {
            if loader { Loader.shared.stop() }
          }
        }
      }
    } else {
      DispatchQueue.main.async {
        UIAlertController.showError(withMessage: "Invalid user Id.", onViewController: self)
        if loader { Loader.shared.stop() }
      }
    }
  }
}

extension TransactionsViewController: UITableViewDelegate, UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return self.transactions.count
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: TransactionCell.self), for: indexPath) as? TransactionCell else {
      return UITableViewCell()
    }
    let trans = self.transactions[indexPath.row]
    cell.configure(type: trans.type ?? 0, date: trans.time ?? "Time not found", amount: trans.amount ?? 0.0)
    return cell
  }
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 60.0
  }
  
  func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
    return .leastNormalMagnitude
  }
  
  func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
    if indexPath.row == self.transactions.count - 1 {
      fetchData(loader: false)
    }
  }
  
}
