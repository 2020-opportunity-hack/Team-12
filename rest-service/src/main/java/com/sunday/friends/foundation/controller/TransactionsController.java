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

/**
 * Transaction Controller
 * @author Mahapatra Manas
 * @version 1.0
 * @since 11-20-2020
 */

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
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(transactionsService.listAll(), HttpStatus.OK);
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/transactions")
    public ResponseEntity<UserTransaction> getTransactionList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {

        if (!TokenVerifier.verify(headers)) {
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
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/depositMonthlyInterest", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> monthlyInterest(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Interest interestObj = interestService.getInterestObject();
            Float rate = interestObj.getInterest() / 12;
            Date currentDate = Calendar.getInstance().getTime();
            Date lastUpdateDate = interestObj.getTimestamp();
            if (lastUpdateDate != null && currentDate.getMonth() == lastUpdateDate.getMonth())
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            List<Users> allUsers = userService.listAll();
            if(transactionsService.calculateMonthlyInterest(allUsers, rate))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
