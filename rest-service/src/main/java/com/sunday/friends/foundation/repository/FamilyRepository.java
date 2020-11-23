package com.sunday.friends.foundation.repository;

import com.sunday.friends.foundation.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
}
