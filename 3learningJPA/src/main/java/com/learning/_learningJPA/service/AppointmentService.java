package com.learning._learningJPA.service;

import com.learning._learningJPA.entity.Appointment;
import com.learning._learningJPA.entity.Doctor;
import com.learning._learningJPA.entity.Patient;
import com.learning._learningJPA.entity.type.AppointmentStatusType;
import com.learning._learningJPA.repository.AppointmentRepository;
import com.learning._learningJPA.repository.DoctorRepository;
import com.learning._learningJPA.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long DoctorId, Long PatientId){
        Doctor doctor = doctorRepository.findById(DoctorId).orElseThrow();
        Patient patient = patientRepository.findById(PatientId).orElseThrow();

        if(appointment.getId() != null) throw new IllegalArgumentException("Appointment Already Present");

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointment.setStatus(AppointmentStatusType.SCHEDULED);

        // to maintain the bidirectional mapping
        patient.getAppointments().add(appointment);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);

        doctor.getAppointments().add(appointment);

        return appointment;
    }
}
