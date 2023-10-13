package com.example.backend.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Response dto for clinic history
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistoryDTOResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * TODO: VER not/nullables
     */
    private String clinicHistoryNumber;

}
