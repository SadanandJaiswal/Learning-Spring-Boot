package com.learning._learningJPA;

import com.learning._learningJPA.entity.Appointment;
import com.learning._learningJPA.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentTests {
    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testCreateAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025,10,23,10,30))
                .reason("Fever")
                .build();

        Appointment newAppointment = appointmentService.createNewAppointment(appointment, 3L, 1L);

        System.out.println(newAppointment);
    }

    @Test
    public void testReAssignAppointmentToAnotherDoctor(){
        Appointment reAsisgnedAppointment = appointmentService.reAssignAppointmentToAnotherDoctor(2L, 2L);
        System.out.println(reAsisgnedAppointment);
    }
}
