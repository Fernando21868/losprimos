package com.example.backend.repository;

import com.example.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonRepository<Model extends Person> extends JpaRepository<Model, Long> {

    Optional<Model> findByEmail(String email);

}
