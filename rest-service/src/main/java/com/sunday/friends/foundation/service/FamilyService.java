package com.sunday.friends.foundation.service;

import com.sunday.friends.foundation.model.Family;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FamilyService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FamilyRepository familyRepository;

    public List<Family> listAll(){
        return familyRepository.findAll();
    }

    public List<Family> getFamilyList(Integer familyId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Family> criteriaQuery = builder.createQuery(Family.class);

        Root<Family> root = criteriaQuery.from(Family.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("familyId"),familyId));
        List<Family> familyList = em.createQuery(criteriaQuery).getResultList();
        return familyList;
    }

    @Transactional
    public boolean addFamily(Integer familyId) {
        em.createNativeQuery("INSERT INTO Family ( familyId) VALUES (?)")
                .setParameter(1, familyId)
                .executeUpdate();
        return true;
    }

    @Transactional
    public Integer addFamily() {
//        em.createNativeQuery("INSERT INTO Family ( address) VALUES (?)")
//                .setParameter(1, "")
//                .executeUpdate();
        Family family = new Family();
        familyRepository.save(family);
        familyRepository.flush();
        return family.getFamilyId();
    }
}
