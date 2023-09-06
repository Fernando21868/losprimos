package com.example.backend.repository;

import com.example.backend.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends IUserRepository<Client> {
}
