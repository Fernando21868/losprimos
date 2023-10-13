package com.example.backend.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Request dto for address
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDtoRequest implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    @NotBlank(message = "Por favor introduzca una direccion ")
    @Size(max = 100, message = "La direccion no debe contener mas de 100 caracteres")
    private String address;

    /**
     * Nullables
     */
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


}
