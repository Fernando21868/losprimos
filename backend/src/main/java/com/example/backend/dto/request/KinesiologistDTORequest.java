package com.example.backend.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Request dto for kinesiologist
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class KinesiologistDTORequest extends EmployeeDtoRequest implements Serializable {

    /**
     * Id
     */
    private Long id;

}
