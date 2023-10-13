package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for client
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClientDtoResponse extends UserDtoResponse implements Serializable {

    /**
     * Id
     */
    private Long id;

}
