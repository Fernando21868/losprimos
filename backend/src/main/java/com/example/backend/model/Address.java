package com.example.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * Entity Address
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * not nullables
     */
    @Column(name = "address")
    private String address;

    /**
     * nullables
     */
    @Column(name = "province")
    private String province;
    @Column(name = "department")
    private String department;
    @Column(name = "municipality")
    private String municipality;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    /**
     * relations
     */
    @OneToOne(mappedBy = "address")
    private Person person;

}
