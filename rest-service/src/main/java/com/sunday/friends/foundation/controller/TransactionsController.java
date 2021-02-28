package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Interest;
import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.UserTransaction;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.InterestService;
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
public class TransactionsController {
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private UserService userService;
    @Autowired
    private InterestService interestService;
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/AllTransactions")
    public ResponseEntity<List<Transactions>> list(@RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(transactionsService.listAll(), HttpStatus.OK);
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/transactions")
    public ResponseEntity<UserTransaction> getTransactionList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {

        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
            Collections.reverse(transactionsList);
            System.out.println(userId);
            UserTransaction userTransaction = new UserTransaction(userId, transactionsList);
            return new ResponseEntity<>(userTransaction, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/depositMonthlyInterest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> monthlyInterest(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Interest interestObj= interestService.getInterestObject();
            Float rate = interestObj.getInterest()/12;
            Date currentDate = Calendar.getInstance().getTime();
            Date lastUpdateDate = interestObj.getTimestamp();
            if(lastUpdateDate!= null && currentDate.getMonth() == lastUpdateDate.getMonth())
                 return new ResponseEntity<>(HttpStatus.CONFLICT);
            List<Users> allUsers = userService.listAll();
            for (Users user : allUsers) {
                if(!user.isActive() || user.isAdmin())
                    continue;

                Integer userId = user.getUserId();
                Float balance = user.getBalance();
                Float interest = balance * rate / 100;
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
