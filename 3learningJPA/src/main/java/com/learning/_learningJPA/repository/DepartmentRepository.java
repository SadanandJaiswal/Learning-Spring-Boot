package com.learning._learningJPA.repository;

import com.learning._learningJPA.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}