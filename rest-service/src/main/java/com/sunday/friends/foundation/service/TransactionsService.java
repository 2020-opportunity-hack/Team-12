package com.sunday.friends.foundation.service;

import com.sunday.friends.foundation.model.Transactions;
import com.sunday.friends.foundation.model.Users;
import com.sunday.friends.foundation.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;
    @PersistenceContext
    private EntityManager em;

    public List<Transactions> listAll(){
        return transactionsRepository.findAll();
    }


    public List<Transactions> getTransactionList(Integer userId, Integer offset, Integer limit) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Transactions> criteriaQuery = builder.createQuery(Transactions.class);

        Root<Transactions> root = criteriaQuery.from(Transactions.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("userId"),userId));

        TypedQuery typedQuery = em.createQuery(criteriaQuery);
        if (null != offset && null != limit) {
            typedQuery.setFirstResult(offset);
            typedQuery.setMaxResults(limit);
        }
        return typedQuery.getResultList();
    }

    @Transactional
    public boolean addTransaction(Transactions transactions) {
        em.createNativeQuery("INSERT INTO Transactions (userId, type, amount, balanceAfterAction, time) VALUES (?,?,?,?,?)")
                .setParameter(1, transactions.getUserId())
                .setParameter(2, transactions.isType())
                .setParameter(3, transactions.getAmount())
                .setParameter(4, transactions.getBalanceAfterAction())
                .setParameter(5,new java.util.Date())
                .executeUpdate();
        return true;
    }
}
