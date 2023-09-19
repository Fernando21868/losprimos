package com.example.backend.repository;

import com.example.backend.model.Psychologist;
import org.springframework.stereotype.Repository;

@Repository
public interface IPsychologistRepository extends IEmployeeRepository<Psychologist> {
}
