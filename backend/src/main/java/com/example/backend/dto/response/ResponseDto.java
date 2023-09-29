package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private Object data;
    private int statusCode;
    private String message;
    private Boolean error;

}
