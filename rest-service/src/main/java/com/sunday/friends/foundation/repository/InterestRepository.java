package com.sunday.friends.foundation.repository;
import com.sunday.friends.foundation.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Interest Repo
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */
public interface InterestRepository extends JpaRepository<Interest, Integer> {
}
