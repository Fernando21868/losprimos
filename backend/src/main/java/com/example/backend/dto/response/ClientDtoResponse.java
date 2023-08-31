package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class ClientDtoResponse extends UserDtoResponse {

    private Long id;
    private String comment;
    private String cart;
    private String favoriteItem;
    private Double amountSpent;

}
