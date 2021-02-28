package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.repository.UserRepository;
import com.sunday.friends.foundation.service.FamilyService;
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
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyService familyService;
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/admin/fetchUsers")
    public List<Users> list(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
            String searchQuery = String.valueOf(json.get("searchQuery"));
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
            return userService.getTotalList(searchQuery, offset, limit);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    //@CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com")
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/admin/deactivatedUsers")
    public List<Users> getDeactivateList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
            String searchQuery = String.valueOf(json.get("searchQuery"));
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
            return userService.getDeactivateList(searchQuery, offset, limit);
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/get_family")
    public List<Users> getFamilyList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
            Integer familyId = Integer.valueOf(json.get("familyId"));
            String searchQuery = String.valueOf(json.get("searchQuery"));
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
            List<Users> userList = userService.getFamilyList(familyId, searchQuery, offset, limit);
            System.out.println(familyId);
            return userList;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/get_user")
    public Users getUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            Users user = userService.getUser(userId);
            System.out.println(userId);
            return user;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/link_family", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateFamilyLink(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/deactivate_user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> deactivateUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/user/onboard", method = RequestMethod.POST)
    @ResponseBody
    public Users onboardUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
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
                newUser.setActive(false);

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
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/transact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> doTransaction(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {

            Integer userId = Integer.valueOf(json.get("userId"));
            Integer amount = Integer.valueOf(json.get("amount"));
            Integer type = 0;
            if(json.get("type").equals("1"))
                type = 1;
            Integer balance = userService.getBalance(userId);
            Integer balanceAfterAction = balance;
            if(type == 1)
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

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return null;
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            String email = json.get("email");
            String name = json.get("name");
            String sfamilyId = json.get("familyId");
            Integer familyId = null;
            if(sfamilyId != null)
                 familyId = Integer.valueOf(json.get("familyId"));
            userService.updateUser(userId, name, email, familyId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?>  deleteUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if(!TokenVerifier.verify(headers)){
            return null;
        }
        try {
                Integer userId = Integer.valueOf(json.get("userId"));
                transactionsService.deleteTransactions(userId);
                userService.deleteUser(userId);

                return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
