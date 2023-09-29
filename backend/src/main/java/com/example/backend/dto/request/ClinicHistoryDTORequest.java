package com.example.backend.dto.request;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistoryDTORequest {

    @Size(max = 15, message = "El numero de historia clinica no contener mas de 15 caracteres")
    private String clinicHistoryNumber;

}
