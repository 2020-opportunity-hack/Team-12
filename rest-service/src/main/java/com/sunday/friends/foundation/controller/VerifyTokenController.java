package com.sunday.friends.foundation.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.NoSuchElementException;

public class VerifyTokenController {
    @RequestMapping(value = "/tokensignin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> doVerify(@RequestParam Map<String, String> json) {
        try {

            String idToken = String.valueOf(json.get("idtoken"));
            String clientId = String.valueOf(json.get("clientId"));
            TokenVerifier.verify(idToken, clientId);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
