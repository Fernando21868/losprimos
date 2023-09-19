package com.example.backend.repository;

import com.example.backend.model.Nurse;
import org.springframework.stereotype.Repository;

@Repository
public interface INurseRepository extends IEmployeeRepository<Nurse> {
}
