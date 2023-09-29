package com.example.backend.dto.response;

import com.example.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoResponse {

    // not nullables
    private String address;

    // nullables
    private String province;
    private String department;
    private String municipality;
    private Double latitude;
    private Double longitude;

}
