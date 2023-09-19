package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository<Model extends User> extends IPersonRepository<Model> {

    Optional<Model> findByUsername(String username);
    Optional<Model> findByVerificationCode(String verificationCode);

}
