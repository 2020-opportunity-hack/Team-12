package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.UserTransaction;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com")
    @GetMapping("/AllTransactions")
    public List<Transactions> list(@RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        return transactionsService.listAll();
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com")
    @GetMapping("/transactions")
    public UserTransaction getTransactionList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {

        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            String offsetString = json.get("offset");
            Integer offset = null;
            if (null != offsetString && !offsetString.isEmpty() && "null" != offsetString) {
                offset = Integer.valueOf(json.get("offset"));
            }
            String limitString = json.get("limit");
            Integer limit = null;
            if (null != limitString && !limitString.isEmpty() && "null" != limitString) {
                limit = Integer.valueOf(json.get("limit"));
            }
            List<Transactions> transactionsList = transactionsService.getTransactionList(userId, offset, limit);
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
