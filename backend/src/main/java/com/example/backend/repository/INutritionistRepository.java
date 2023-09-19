package com.example.backend.repository;

import com.example.backend.model.Nutritionist;
import org.springframework.stereotype.Repository;

@Repository
public interface INutritionistRepository extends IEmployeeRepository<Nutritionist> {
}
