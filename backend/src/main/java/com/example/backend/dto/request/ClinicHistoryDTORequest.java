package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicHistoryDTORequest {

    @Size(max = 15, message = "El numero de historia clinica no contener mas de 15 caracteres")
    private String clinicHistoryNumber;

}
