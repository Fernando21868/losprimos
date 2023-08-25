package com.example.backend.repository;

import com.example.backend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import javax.validation.constraints.Email;
import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    Optional<Client> findByUsername(String username);
    Optional<Client> findByVerificationCode(String verificationCode);

}
