package com.example.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDtoRequest {

    @Size(max = 100, message = "La provincia no debe contener mas de 100 caracteres")
    private String province;

    @Size(max = 100, message = "El departamento no debe contener mas de 100 caracteres")
    private String department;

    @Size(max = 100, message = "La municipalidad no debe contener mas de 100 caracteres")
    private String municipality;

    @Range(min = -90, max = 90)
    private Double latitude;

    @Range(min = -180, max = 180)
    private Double longitude;

    @Size(max = 100, message = "La direccion no debe contener mas de 100 caracteres")
    private String address;

}
