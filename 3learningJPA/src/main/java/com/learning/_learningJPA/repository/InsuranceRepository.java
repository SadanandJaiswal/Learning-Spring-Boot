package com.learning._learningJPA.repository;

import com.learning._learningJPA.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}