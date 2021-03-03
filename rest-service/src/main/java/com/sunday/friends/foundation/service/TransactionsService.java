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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Transaction Service
 * @author  Mahapatra Manas, Roy Abhinav
 * @version 1.0
 * @since   11-20-2020
 */
@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private UserService userService;
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
        transactionsRepository.save(transactions);
        transactionsRepository.flush();
        return true;
    }

    public void deleteTransactions(Integer userId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Transactions> criteriaQuery = builder.createQuery(Transactions.class);

        Root<Transactions> root = criteriaQuery.from(Transactions.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("userId"),userId));

        TypedQuery typedQuery = em.createQuery(criteriaQuery);
        List<Transactions> list = typedQuery.getResultList();
        for(Transactions transactions: list)
            transactionsRepository.deleteById(transactions.getTransactionid());

    }

    public boolean calculateMonthlyInterest(List<Users> allUsers, Float rate) {
        for (Users user : allUsers) {
            if (!user.isActive() || user.isAdmin())
                continue;

            Integer userId = user.getUserId();
            Float balance = user.getBalance();
            Float interest = balance * rate / 100;
            balance += interest;
            Date date = Calendar.getInstance().getTime();
            Transactions transactions = new Transactions(userId, 2, interest, balance, date);
            if (addTransaction(transactions)) {
                userService.updateBalance(userId, balance);
            } else
                return false;
        }
        return true;
    }

    public boolean doTransaction(Integer userId, Float amount, Integer type) {
        Float balance = userService.getBalance(userId);
        Float balanceAfterAction = balance;
        if (type == 1)
            balanceAfterAction += amount;
        else
            balanceAfterAction -= amount;
        Date date = Calendar.getInstance().getTime();
        Transactions transactions = new Transactions(userId, type, amount, balanceAfterAction, date);
        if (addTransaction(transactions)) {
            userService.updateBalance(userId, balanceAfterAction);
            return true;
        } else
            return false;
    }
}
