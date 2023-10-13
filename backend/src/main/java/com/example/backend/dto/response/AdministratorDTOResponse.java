package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for administrator
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AdministratorDTOResponse extends EmployeeDtoResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

}
