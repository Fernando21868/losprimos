package com.example.backend.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class ResponseErrorDto extends ResponseDto {

    public ResponseErrorDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }
}
