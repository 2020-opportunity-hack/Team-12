package com.sunday.friends.foundation.repository;

import com.sunday.friends.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
