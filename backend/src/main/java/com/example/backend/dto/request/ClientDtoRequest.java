package com.example.backend.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Request dto for client
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClientDtoRequest extends PersonDTORequest implements Serializable {

    /**
     * Id
     */
    private Long id;

}
