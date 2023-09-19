package com.example.backend.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class NutritionistDTOResponse extends EmployeeDtoResponse{

    private Long id;

}
