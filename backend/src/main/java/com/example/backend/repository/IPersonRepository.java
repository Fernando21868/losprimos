package com.example.backend.repository;

import com.example.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IPersonRepository<Model extends Person> extends JpaRepository<Model, Long> {

    Optional<Model> findByEmailAndEmailIsNotNull(String email);

}
