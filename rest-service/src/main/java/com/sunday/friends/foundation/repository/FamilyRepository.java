package com.sunday.friends.foundation.repository;

import com.sunday.friends.foundation.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Family Repo
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */
public interface FamilyRepository extends JpaRepository<Family, Integer> {
}
