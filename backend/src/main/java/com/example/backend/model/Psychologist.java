package com.example.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
public class Psychologist extends Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
