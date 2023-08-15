package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseSuccessDto<T> extends ResponseDto {

    public ResponseSuccessDto(Object data, int statusCode, String message, Boolean error) {
        super(data, statusCode, message, error);
    }
}
