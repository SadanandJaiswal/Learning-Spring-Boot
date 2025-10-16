package com.learning._learningJPA.repository;

import com.learning._learningJPA.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}