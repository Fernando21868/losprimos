package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDtoResponse extends UserDtoResponse {

    private Long id;
    private String type;
    private String permission;
    private Date vacations;

    public EmployeeDtoResponse(String email, String firstName, String lastName, String phoneNumber, String birthday, String address, String username, Long id, String type, String permission, Date vacations) {
        super(email, firstName, lastName, phoneNumber, birthday, address, username);
        this.id = id;
        this.type = type;
        this.permission = permission;
        this.vacations = vacations;
    }
}
