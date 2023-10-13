package com.example.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Request dto for doctor
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DoctorDTORequest extends EmployeeDtoRequest implements Serializable {

    /**
     * Id
     */
    private Long id;

}
