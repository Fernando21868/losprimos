package com.example.backend.dto.request;

import lombok.*;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Request dto for clinic history
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistoryDTORequest implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * TODO: VER not/nullables
     */
    @Size(max = 15, message = "El numero de historia clinica no contener mas de 15 caracteres")
    private String clinicHistoryNumber;

}
