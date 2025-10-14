package com.learning._learningJPA.service;

import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Patient getPatientById(Long id){
        Patient p1 = patientRepository.findById(id).orElseThrow();
        // This Data will be stored in persistence context

        Patient p2 = patientRepository.findById(id).orElseThrow();
        System.out.println(p1==p2);

        p1.setName("Saddy");

        // no need to save, it get saved automatically
        // patientRepository.save(p1);
        return p1;
    }
}
