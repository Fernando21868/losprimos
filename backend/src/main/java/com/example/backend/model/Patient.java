package com.example.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "patient")
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "book_enlistment")
    private String bookEnlistment;
    @Column(name = "book_civic")
    private String bookCivic;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )
    @JoinColumn(name = "clinic_history_id", referencedColumnName = "id")
    private ClinicHistory clinicHistory;

}
