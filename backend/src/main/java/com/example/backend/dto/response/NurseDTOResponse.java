package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for nurse
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class NurseDTOResponse extends EmployeeDtoResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

}
