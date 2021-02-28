package com.sunday.friends.foundation.service;
import com.sunday.friends.foundation.model.Interest;
import com.sunday.friends.foundation.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;
    @PersistenceContext
    private EntityManager em;

    public Interest getInterestObject(){
        List<Interest> interestList = interestRepository.findAll();
        return interestList.get(interestList.size()-1);

    }
}
