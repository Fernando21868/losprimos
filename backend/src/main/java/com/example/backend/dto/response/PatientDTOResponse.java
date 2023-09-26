package com.example.backend.dto.response;

import com.example.backend.dto.request.ClinicHistoryDTORequest;
import com.example.backend.dto.request.PersonDTORequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class PatientDTOResponse extends PersonDTOResponse{

    // nullables
    private Integer identityCard;
    private Integer bookEnlistment;
    private Integer bookCivic;

    // relations
    private ClinicHistoryDTOResponse clinicHistoryDTOResponse;

}