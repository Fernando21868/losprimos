package com.example.backend.repository;

import com.example.backend.model.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends IEmployeeRepository<Doctor> {

}
