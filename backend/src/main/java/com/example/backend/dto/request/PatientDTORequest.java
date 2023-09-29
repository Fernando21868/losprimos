package com.example.backend.dto.request;

import com.example.backend.model.ClinicHistory;
import com.example.backend.model.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class PatientDTORequest extends PersonDTORequest{

    // nullables
    @Size(max = 15, message = "La cedula de identidad no contener mas de 15 caracteres")
    private String identityCard;
    @Size(max = 15, message = "La libreta de enrolamiento no debe contener mas de 15 caracteres")
    private String bookEnlistment;
    @Size(max = 15, message = "La libreta civica no debe contener mas de 15 caracteres")
    private String bookCivic;

    // relations
    private ClinicHistoryDTORequest clinicHistory;

}
