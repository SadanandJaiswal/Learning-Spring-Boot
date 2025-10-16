package com.learning._learningJPA.repository;

import com.learning._learningJPA.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}