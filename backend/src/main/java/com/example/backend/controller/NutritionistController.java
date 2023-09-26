package com.example.backend.controller;

import com.example.backend.dto.request.NutritionistDTORequest;
import com.example.backend.dto.response.NutritionistDTOResponse;
import com.example.backend.exceptions.NutritionistNotFoundException;
import com.example.backend.model.Nutritionist;
import com.example.backend.repository.INutritionistRepository;
import com.example.backend.service.Implementation.NutritionistService;
import com.example.backend.service.Interface.INutritionistService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nutritionists")
public class NutritionistController extends EmployeeController<
        NutritionistDTOResponse,
        NutritionistDTORequest,
        Nutritionist,
        INutritionistRepository,
        NutritionistNotFoundException,
        INutritionistService,
        NutritionistService>{
    public NutritionistController(NutritionistService service) {
        super(service);
    }
}
