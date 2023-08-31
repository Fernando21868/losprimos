package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class EmployeeDtoResponse extends UserDtoResponse {

    private Long id;
    private String type;
    private Date vacations;
    private Integer dni;

}
