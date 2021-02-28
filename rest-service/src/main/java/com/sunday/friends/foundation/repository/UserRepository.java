package com.sunday.friends.foundation.repository;

import com.sunday.friends.foundation.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * User Repo
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
}
