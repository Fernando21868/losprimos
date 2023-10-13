package com.example.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * Entity for role
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Entity rol
     */
    @Enumerated(EnumType.STRING)
    private RoleEnum rol;

}
