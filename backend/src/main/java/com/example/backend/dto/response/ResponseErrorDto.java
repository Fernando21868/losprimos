package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseErrorDto extends ResponseDto {
    public ResponseErrorDto(int statusCode, String message) {
        super(statusCode, message);
    }
}
