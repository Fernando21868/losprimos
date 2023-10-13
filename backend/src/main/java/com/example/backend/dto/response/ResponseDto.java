package com.example.backend.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * Response dto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {

    /**
     * data: data or objects, lists, errors
     * statusCode: code to respond
     * message: message to send for response
     * error: if occur an error or not
     */
    private Object data;
    private int statusCode;
    private String message;
    private Boolean error;

}
