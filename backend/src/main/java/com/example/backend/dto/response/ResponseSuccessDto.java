package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseSuccessDto<T> extends ResponseDto {

    private T responseData;

    public ResponseSuccessDto(int statusCode, String message, T responseData) {
        super(statusCode, message);
        this.responseData = responseData;
    }
}
