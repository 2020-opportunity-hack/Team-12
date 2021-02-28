package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Family;
import com.sunday.friends.foundation.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class FamilyController {

    @Autowired
    private FamilyService familyService;
    @CrossOrigin("http://ec2-184-169-189-74.us-west-1.compute.amazonaws.com:8081")
    @GetMapping("/Family")
    public ResponseEntity<List<Family>> list(@RequestParam Map<String, String> json, @RequestHeader Map<String, String> headers) throws GeneralSecurityException, IOException {

        if(!TokenVerifier.verify(headers)){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

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
        return new ResponseEntity<>(familyService.getTotalList(offset, limit), HttpStatus.OK);
    }
}
