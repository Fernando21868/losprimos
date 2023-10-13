package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for success
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseSuccessDto<T> extends ResponseDto implements Serializable {

    /**
     * Constructor for success dto response
     * @param data data or objects, lists
     * @param statusCode code to respond
     * @param message message to send for response
     * @param error if occur an error or not
     */
    public ResponseSuccessDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }
}
