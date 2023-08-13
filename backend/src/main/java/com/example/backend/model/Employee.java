package com.example.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String permission;
    private Date vacations;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Employee(Long id, String email, String firstName, String lastName, String phoneNumber, String birthday, String address, String username, String password, List<Role> roles, Long id1, String type, String permission, Date vacations, Timestamp createdAt, Timestamp updatedAt) {
        super(id, email, firstName, lastName, phoneNumber, birthday, address, username, password, roles);
        this.id = id1;
        this.type = type;
        this.permission = permission;
        this.vacations = vacations;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
