package com.sunday.friends.foundation.repository;
import com.sunday.friends.foundation.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Transaction Repo
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
