package com.sunday.friends.foundation.repository;
import com.sunday.friends.foundation.model.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
