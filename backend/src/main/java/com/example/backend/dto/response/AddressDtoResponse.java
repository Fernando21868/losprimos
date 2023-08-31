package com.example.backend.dto.response;

import com.example.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoResponse {

    private Long id;
    private String province;
    private String department;
    private String municipality;
    private Double latitude;
    private Double longitude;
    private String address;

}
