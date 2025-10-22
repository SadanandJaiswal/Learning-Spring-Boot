package com.learning._learningJPA;

import com.learning._learningJPA.entity.Insurance;
import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class InsuranceTests {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public void testInsurance(){
        // Insurance insurance = new Insurance(null, "HDFC_1234", "HDFC", LocalDate.of(2021, 12, 12), );

        // Avoid this, we will use Builder

        Insurance insurance = Insurance.builder()
                .policyNumber("HDFC_12345678")
                .validUntil(LocalDate.of(2021,12,12))
                .provider("HDFC")
                .build();

        // Here we have not created the Insurance with id 4 but as we have used transaction and cascade so first insurance will be created and then linked ot the patient

        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 6L);
        System.out.println(patient);

        Patient newPatient = insuranceService.dissaccosiateInsuranceFromPatient(patient.getId());
        System.out.println(newPatient);
    }
}
