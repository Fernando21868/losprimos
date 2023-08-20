package com.example.backend.repository;

import com.example.backend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);

    Client findByUsername(String username);

}
