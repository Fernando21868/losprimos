package com.example.backend.repository;

import com.example.backend.model.Patient;
import com.example.backend.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends IPersonRepository<Patient> {
}
