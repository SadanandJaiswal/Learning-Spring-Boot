package com.learning._learningJPA;

import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.entity.dto.BloodGroupCountResponseEntity;
import com.learning._learningJPA.entity.type.BloodGroupType;
import com.learning._learningJPA.repository.PatientRepository;
import com.learning._learningJPA.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.swing.*;
import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class PatientTests {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testCreatePatient() {
        Patient patient = new Patient(
                null,
                "Rahul",
                LocalDate.of(2006, 9, 19),
                "rahul2@gmail.com",
                "Male",
                BloodGroupType.A_POSITIVE
        );

        patientRepository.save(patient);
    }


    @Test
    public void testPatientRepository(){
        List<Patient> patientList = patientRepository.findAll();
        patientList.forEach(patient -> System.out.println(patient.toString()));
    }

    @Test
    public void testPatientById(){
        Patient patient = patientService.getPatientById(108L);
        System.out.println(patient);
    }

    @Test
    public void testPatientByName(){
        Patient patient = patientRepository.findByName("Rahul");
        System.out.println(patient);
    }

    @Test
    public void testPatientByNameOrEmail(){
        List<Patient> patient = patientRepository.findByNameOrEmail("Rahul Sharma","priya.verma@example.com");
        System.out.println(patient);
    }

    @Test
    public void testPatientByGender(){
        List<Patient> patientList = patientRepository.findByGender("Female");
        patientList.forEach(patient -> System.out.println(patient));
    }

    @Test
    public void testPatientByBirthDate(){
        List<Patient> patientList = patientRepository.findByBirthDate(LocalDate.of(2000,06,30));
        patientList.forEach(patient -> System.out.println(patient));
    }

    @Test
    public void testPatientByBirthDateBetween(){
        List<Patient> patientList = patientRepository.findByBirthDateBetween(LocalDate.of(1975,01,01), LocalDate.of(1999,12,31));
        patientList.forEach(patient -> System.out.println(patient));
    }

    /*
    @Test
    public void testPatientByBirthDateNotBetween() {
        List<Patient> patientList = patientRepository.findByBirthDateNotBetween(
                LocalDate.of(1975, 1, 1),
                LocalDate.of(1999, 12, 31)
        );
        patientList.forEach(patient -> System.out.println(patient));
    }


     */
    @Test
    public void testPatientByBirthDateAfter() {
        List<Patient> patientList = patientRepository.findByBirthDateAfter(
                LocalDate.of(1999, 12, 31)
        );
        patientList.forEach(patient -> System.out.println(patient));
    }


    @Test
    public void testPatientByBirthDateBefore() {
        List<Patient> patientList = patientRepository.findByBirthDateBefore(
                LocalDate.of(1995, 1, 1)
        );
        patientList.forEach(patient -> System.out.println(patient));
    }

    @Test
    public void testPatientByBloodGroup(){
        List<Patient> patientList = patientRepository.getPatientByBloodGroup(BloodGroupType.A_POSITIVE);
        patientList.forEach(patient -> System.out.println(patient));
    }

    @Test
    public void testPatientByBornAfterDate(){
        List<Patient> patientList = patientRepository.findByBornAfterDate(LocalDate.of(2000,01,01));
        patientList.forEach(patient -> System.out.println(patient));
    }

    @Test
    public void testCountPatientByBloodGroup(){
        List<BloodGroupCountResponseEntity> patientList = patientRepository.countPatientByBloodGroup();
        patientList.forEach(patient -> System.out.println(patient.toString()));
    }

    @Test
    public void testGetAllPatients(){
        printPageData(patientRepository.findAllPatient(PageRequest.of(0, 5)), "First Five Patients");

        Page<Patient> patientList2 = patientRepository.findAllPatient(PageRequest.of(1, 5, Sort.by("name")));
        System.out.println("Second Five Patients");
        patientList2.forEach(System.out::println);

        Page<Patient> patientList3 = patientRepository.findAllPatient(PageRequest.of(2, 5, Sort.by("name").ascending().and(Sort.by("birth_date").descending())));
        System.out.println("Third Five Patients");
        patientList3.forEach(System.out::println);
    }

    private void printPageData(Page<Patient> page, String title) {
        System.out.println("========== " + title + " ==========");
        System.out.println("Page Number: " + page.getNumber());
        System.out.println("Page Size: " + page.getSize());
        System.out.println("Total Pages: " + page.getTotalPages());
        System.out.println("Total Elements: " + page.getTotalElements());
        System.out.println("Is First: " + page.isFirst());
        System.out.println("Is Last: " + page.isLast());
        System.out.println("Number of Elements in this Page: " + page.getNumberOfElements());
        System.out.println("----- Patient Details -----");
        page.forEach(System.out::println);
    }

    @Test
    public void testUpdateNameById(){
        int patientAffected = patientRepository.updateNameById("Anjali Sharma", 113L);
        System.out.println("Update Successful, "+patientAffected+" Patient Affected");
    }
}
