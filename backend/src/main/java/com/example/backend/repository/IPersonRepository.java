package com.example.backend.repository;

import com.example.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IPersonRepository<Model extends Person> extends JpaRepository<Model, Long> {

    /**
     * Query to find an email
     * @param email to be found
     * @return the email found
     */
    @Query("SELECT p FROM Person p WHERE p.email = :email AND p.email IS NOT NULL")
    Optional<Model> findByEmailAndEmailIsNotNull(@Param("email") String email);

    /**
     * Query to find a dni
     * @param dni to be found
     * @return the dni found
     */
    @Query("SELECT p FROM Person p WHERE p.dni = :dni AND p.dni IS NOT NULL")
    Optional<Model> findByDniAndDniIsNotNull(@Param("dni") String dni);

}
