package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository<T extends User> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);
    Optional<T> findByUsername(String username);
    Optional<T> findByVerificationCode(String verificationCode);

}
