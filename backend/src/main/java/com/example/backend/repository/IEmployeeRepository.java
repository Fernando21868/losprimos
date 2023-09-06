package com.example.backend.repository;

import com.example.backend.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends IUserRepository<Employee> {
}
