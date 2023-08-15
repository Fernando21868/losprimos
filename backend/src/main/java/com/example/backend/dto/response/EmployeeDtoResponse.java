package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDtoResponse extends UserDtoResponse {

    private Long id;
    private String type;
    private String permission;
    private Date vacations;
    private Integer dni;

    public EmployeeDtoResponse(String email, String firstName, String lastName, String phoneNumber, String birthday, String address, String username, List<Role> roles, Long id, String type, String permission, Date vacations, Integer dni) {
        super(email, firstName, lastName, phoneNumber, birthday, address, username, roles);
        this.id = id;
        this.type = type;
        this.permission = permission;
        this.vacations = vacations;
        this.dni = dni;
    }
}
