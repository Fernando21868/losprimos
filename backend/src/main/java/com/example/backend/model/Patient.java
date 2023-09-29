package com.example.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Entity Patient
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "patient")
public class Patient extends Person {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Attributes
     */
    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "book_enlistment")
    private String bookEnlistment;
    @Column(name = "book_civic")
    private String bookCivic;

    /**
     * Relations
     */
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )
    @JoinColumn(name = "clinic_history_id", referencedColumnName = "id")
    private ClinicHistory clinicHistory;

}
