package com.learning._learningJPA.service;

import com.learning._learningJPA.entity.Insurance;
import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.repository.InsuranceRepository;
import com.learning._learningJPA.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    // We want all the operation to complete fully or does not complete any, so we will use Transaction.
    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new EntityNotFoundException("Patient not found with id: "+patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        return  patient;
    }
}
