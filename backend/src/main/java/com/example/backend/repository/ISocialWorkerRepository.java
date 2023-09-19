package com.example.backend.repository;

import com.example.backend.model.SocialWorker;
import org.springframework.stereotype.Repository;

@Repository
public interface ISocialWorkerRepository extends IEmployeeRepository<SocialWorker> {
}
