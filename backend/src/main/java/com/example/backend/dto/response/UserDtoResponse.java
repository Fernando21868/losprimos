package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Response dto for user
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserDtoResponse extends PersonDTOResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

    /**
     * Not nullables
     */
    private String username;
    private List<Role> roles;

}
