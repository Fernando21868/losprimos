package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository<Model extends User> extends IPersonRepository<Model> {

    /**
     * Query to find a user by username
     * @param username to find a user
     * @return user
     */
    Optional<Model> findByUsernameAndUsernameIsNotNull(String username);

    /**
     * Query to find a user by token
     * @param verificationCode to find a user
     * @return user
     */
    Optional<Model> findByVerificationCodeAndVerificationCodeIsNotNull(String verificationCode);

}
