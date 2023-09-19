package com.example.backend.repository;

import com.example.backend.model.Employee;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Repository
@Configuration
public interface IEmployeeRepository<Model extends Employee> extends IUserRepository<Model> {
}
