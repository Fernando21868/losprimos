package com.example.backend.repository;

import com.example.backend.model.Kinesiologist;
import org.springframework.stereotype.Repository;

@Repository
public interface IKinesiologistRepository extends IEmployeeRepository<Kinesiologist> {
}
