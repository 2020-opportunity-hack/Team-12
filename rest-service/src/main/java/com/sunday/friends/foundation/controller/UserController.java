package com.sunday.friends.foundation.controller;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.service.FamilyService;
import com.sunday.friends.foundation.service.TransactionsService;
import com.sunday.friends.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
/**
 * The User Controller
 * @author  Mahapatra Manas, Roy Abhinav
 * @version 1.0
 * @since   11-20-2020
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private FamilyService familyService;

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/admin/fetchUsers")
    public ResponseEntity<List<Users>> list(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(userService.getTotalList(searchQuery, offset, limit), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/admin/deactivatedUsers")
    public ResponseEntity<List<Users>> getDeactivateList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(userService.getDeactivateList(searchQuery, offset, limit), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/get_family")
    public ResponseEntity<List<Users>> getFamilyList(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/user/get_user")
    public ResponseEntity<Users> getUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            Users user = userService.getUser(userId);
            System.out.println(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/link_family", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateFamilyLink(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            Integer familyId = Integer.valueOf(json.get("familyId"));
            System.out.println(userId);
            System.out.println(familyId);

            if (userService.updateFamilyLink(userId, familyId))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/deactivate_user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> deactivateUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            String deactivate = json.get("deactivate");

            if (userService.deactivateUser(userId, deactivate))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/user/onboard", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Users> onboardUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers, json.get("email"))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        try {
            // Existing User
            String email = json.get("email");
            Users existingUser = userService.getUser(email);
            //Check for InActive
            if (existingUser != null && !existingUser.isActive())
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            // Existing Return
            if (existingUser != null)
                return new ResponseEntity<>(existingUser, HttpStatus.OK);

            // New User
            Integer familyId;
            if (json.get("familyId") == null)
                familyId = familyService.addFamily();
            else
                familyId = Integer.valueOf(json.get("familyId"));

            Users newUser = new Users(json.get("name"), json.get("email"), familyId, json.get("imageUrl"));
            newUser.setActive(false);

            if (familyService.getFamilyList(familyId).size() == 0)
                familyService.addFamily(familyId);
            return new ResponseEntity<>(userService.onboardUser(newUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/transact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> doTransaction(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {

            Integer userId = Integer.valueOf(json.get("userId"));
            Float amount = Float.valueOf(json.get("amount"));
            Integer type = 0;
            if (json.get("type").equals("1"))
                type = 1;
            if(transactionsService.doTransaction(userId, amount, type))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            String email = json.get("email");
            String name = json.get("name");
            String sfamilyId = json.get("familyId");
            Integer familyId = null;
            if (sfamilyId != null)
                familyId = Integer.valueOf(json.get("familyId"));
            userService.updateUser(userId, name, email, familyId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> deleteUser(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Integer userId = Integer.valueOf(json.get("userId"));
            transactionsService.deleteTransactions(userId);
            userService.deleteUser(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @RequestMapping(value = "/admin/bulkTransact", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> bulkTransact(@RequestParam("file") MultipartFile file, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {
        if (!TokenVerifier.verify(headers)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (file.getContentType().equals(UserService.EXCEL_TYPE)) {
            try {
                userService.bulkTransact(file.getInputStream());
                return new ResponseEntity("Success", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity("Please upload an Excel file!", HttpStatus.BAD_REQUEST);
        }
    }

}
