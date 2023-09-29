package com.example.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clinic_history")
public class ClinicHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clinicHistoryNumber")
    private String clinicHistoryNumber;

    @OneToOne(mappedBy = "clinicHistory")
    private Patient patient;
}
