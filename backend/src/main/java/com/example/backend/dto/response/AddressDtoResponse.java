package com.example.backend.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Response dto for address
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    private String address;

    /**
     * Nullables
     */
    private String province;
    private String department;
    private String municipality;
    private Double latitude;
    private Double longitude;

}
