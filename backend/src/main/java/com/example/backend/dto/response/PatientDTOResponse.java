package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for patient
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PatientDTOResponse extends PersonDTOResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Nullables
     */
    private Integer identityCard;
    private Integer bookEnlistment;
    private Integer bookCivic;

    /**
     * Relations
     */
    private ClinicHistoryDTOResponse clinicHistory;

}
