package com.example.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private String cart;
    private String favoriteItem;
    private Double amountSpent;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Client(Long id, String email, String firstName, String lastName, String phoneNumber, Date birthday, String address, String username, String password, List<Role> roles, Long id1, String comment, String cart, String favoriteItem, Double amountSpent, Timestamp createdAt, Timestamp updatedAt) {
        super(id, email, firstName, lastName, phoneNumber, birthday, address, username, password, roles);
        this.id = id1;
        this.comment = comment;
        this.cart = cart;
        this.favoriteItem = favoriteItem;
        this.amountSpent = amountSpent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
