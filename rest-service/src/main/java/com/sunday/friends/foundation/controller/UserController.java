package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.FamilyService;
import com.sunday.friends.foundation.service.TransactionsService;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private FamilyService familyService;

    @GetMapping("/admin/fetchUsers")
    public List<Users> list(){
        try {
            return userService.listAll();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/user/get_family")
    public List<Users> getFamilyList(@RequestParam Map<String, String> json){
        try {
            Integer familyId = Integer.valueOf(json.get("familyId"));
            List<Users> userList = userService.getFamilyList(familyId);
            System.out.println(familyId);
            return userList;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @RequestMapping(value = "/admin/link_family", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateFamilyLink(@RequestParam Map<String, String> json) {
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            Integer familyId = Integer.valueOf(json.get("familyId"));
            System.out.println(userId);
            System.out.println(familyId);

            if(userService.updateFamilyLink(userId, familyId))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/admin/deactivate_user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> deactivateUser(@RequestParam Map<String, String> json) {
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            String deactivate = json.get("deactivate");

            if(userService.deactivateUser(userId, deactivate))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user/onboard", method = RequestMethod.POST)
    @ResponseBody
    public Users onboardUser(@RequestParam Map<String, String> json) {
            try {
                // Existing User
                String email = json.get("email");
                Users existingUser = userService.getUser(email);
                //Check for InActive
                if(existingUser != null && !existingUser.isActive())
                    return null;
                // Existing Return
                if(existingUser != null)
                    return existingUser;

                // New User
                Integer familyId;
                if(json.get("familyId") == null)
                    familyId = familyService.addFamily();
                else
                    familyId = Integer.valueOf(json.get("familyId"));

                Users newUser = new Users(json.get("name"), json.get("email"), familyId, json.get("imageUrl"));
                newUser.setActive(true);

                if(familyService.getFamilyList(familyId).size() == 0)
                    familyService.addFamily(familyId);
//                if(userService.onboardUser(newUser))
//                    return new ResponseEntity<>(HttpStatus.OK);
//                else
//                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                return userService.onboardUser(newUser);
            }

            catch (Exception e){
                return null;
            }
    }

    @RequestMapping(value = "/admin/transact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> doTransaction(@RequestParam Map<String, String> json) {
        try {

            Integer userId = Integer.valueOf(json.get("userId"));
            Integer amount = Integer.valueOf(json.get("amount"));
            boolean type = false;
            if(json.get("type").equals("1"))
                type = true;
            Integer balance = userService.getBalance(userId);
            Integer balanceAfterAction = balance;
            if(type == true)
                balanceAfterAction += amount;
            else
                balanceAfterAction -= amount;
            Date date = Calendar.getInstance().getTime();
            Transactions transactions = new Transactions(userId, type,  amount,  balanceAfterAction,  date);
            if(transactionsService.addTransaction(transactions)) {
                userService.updateBalance(userId, balanceAfterAction);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
