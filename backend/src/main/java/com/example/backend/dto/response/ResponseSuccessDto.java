package com.example.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class ResponseSuccessDto<T> extends ResponseDto {

    public ResponseSuccessDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }
}
