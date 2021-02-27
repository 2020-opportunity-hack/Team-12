package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.UserTransaction;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.TransactionsService;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserService userService;
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/AllTransactions")
    public List<Transactions> list(@RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        return transactionsService.listAll();
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
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

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/monthlyInterest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> monthlyInterest(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer rate= 5;
            List<Users> allUsers = userService.getTotalList(null, null, null);
            for (Users user : allUsers) {

                Integer userId = Integer.valueOf(json.get("userId"));
                Integer balance = userService.getBalance(userId);
                Integer interest = balance * rate / 100;
                balance += interest;
                Date date = Calendar.getInstance().getTime();
                Transactions transactions = new Transactions(userId, 2, interest, balance, date);
                if (transactionsService.addTransaction(transactions)) {
                    userService.updateBalance(userId, balance);
                } else
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }

        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
