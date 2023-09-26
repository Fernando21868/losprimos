package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // not nullables
    @Column(name = "address", nullable = false)
    private String address;

    // nullables
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

    // relations
    @OneToOne(mappedBy = "address")
    private Person person;

}
