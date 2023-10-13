package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


/**
 * Response DTO for employee
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EmployeeDtoResponse extends UserDtoResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    private String enrollment;
    private String type;

}
