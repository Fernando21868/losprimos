package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Response dto for errors
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseErrorDto extends ResponseDto implements Serializable {

    /**
     * Constructor for error dto response
     * @param data data or errors
     * @param statusCode code to respond
     * @param message message to send for response
     * @param error if occur an error or not
     */
    public ResponseErrorDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }

}
