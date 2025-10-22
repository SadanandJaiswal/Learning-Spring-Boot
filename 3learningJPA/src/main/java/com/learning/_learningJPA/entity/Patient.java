package com.learning._learningJPA.entity;

import com.learning._learningJPA.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(
        name="patient",
        uniqueConstraints = {
            @UniqueConstraint(name="unique_patient_email", columnNames = {"email"}),
            @UniqueConstraint(name = "unique_patient_name_birthDate", columnNames = {"name", "birthDate"})
        },
        indexes = {
                @Index(name = "idx_patient_name", columnList = "name"),
                @Index(name = "idx_patient_name_email", columnList = "name, email"),
                @Index(name = "idx_patient_email", columnList = "email", unique = true)
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude   // This field will Excluded when calling toString Method
    private Long id;

    @Column(name = "name", unique = false, length = 64)
    private String name;

    private LocalDate birthDate;

    private String email;

    public Patient(Long id, String name, LocalDate birthDate, String email, String gender, BloodGroupType bloodGroup) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.bloodGroup = bloodGroup;  // now correctly assigned
    }

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)    // other wise it will store 0,1,2 insted of value
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
}
