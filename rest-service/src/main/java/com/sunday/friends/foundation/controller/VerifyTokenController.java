package com.sunday.friends.foundation.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.NoSuchElementException;

public class VerifyTokenController {
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com")
    @RequestMapping(value = "/tokensignin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> doVerify(@RequestParam Map<String, String> json) {
        try {

            String idToken = String.valueOf(json.get("idtoken"));
            String clientId = String.valueOf(json.get("clientId"));
            //TokenVerifier.verify(idToken, clientId);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }
}
