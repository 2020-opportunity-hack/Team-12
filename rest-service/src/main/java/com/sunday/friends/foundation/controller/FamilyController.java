package com.sunday.friends.foundation.controller;

import com.sunday.friends.foundation.model.Family;
import com.sunday.friends.foundation.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/Family")
    public List<Family> list(){
        return familyService.listAll();
    }
}
