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

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "person")
public class Person {

    // id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // not nullables
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // nullables
    @Column(name = "dni")
    private String dni;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "age")
    private String age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "social_work")
    private String socialWork;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    // relations
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;
    @ManyToOne
    private Person person;
    @OneToMany(mappedBy="person")
    private Set<Person> personSet;

    // additional
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
