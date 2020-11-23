package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.UserTransaction;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/AllTransactions")
    public List<Transactions> list(){

        return transactionsService.listAll();
    }

    @GetMapping("/transactions")
    public UserTransaction getTransactionList(@RequestParam Map<String, String> json){
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            List<Transactions> transactionsList = transactionsService.getTransactionList(userId);
            System.out.println(userId);
            UserTransaction userTransaction = new UserTransaction(userId, transactionsList);
            return userTransaction;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


}
