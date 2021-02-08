package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Family;
import com.sunday.friends.foundation.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/Family")
    public List<Family> list(@RequestParam Map<String, String> json){
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
        return familyService.getTotalList(offset, limit);
    }
}
