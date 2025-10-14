package com.learning._learningJPA.repository;

import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByName(String name);
    List<Patient> findByBirthDate(LocalDate birthDate);
    List<Patient> findByNameOrEmail(String name, String email);
    List<Patient> findByGender(String gender);
    List<Patient> findByBirthDateBetween(LocalDate from, LocalDate till);
    List<Patient> findByBirthDateBefore(LocalDate birthDate);
    List<Patient> findByBirthDateAfter(LocalDate birthDate);

    @Query("select p from Patient p where p.bloodGroup = ?1")
    List<Patient> getPatientByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroupType);

    @Query("select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    @Query("select p.bloodGroup, count(p) from Patient p group by p.bloodGroup")
    List<Object[]> countPatientByBloodGroup();

    @Query(value = "select * from patient", nativeQuery = true)
    List<Patient> findAllPatient();

    // Update will return how many rows have been affected
    @Transactional
    @Modifying
    @Query("update Patient p set p.name = :name where p.id = :id")
    int updateNameById(@Param("name") String name, @Param("id") Long id);
}
