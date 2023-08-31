package com.example.backend.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class ResponseErrorDto extends ResponseDto {

    public ResponseErrorDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }

}
