package com.example.backend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDtoResponse extends UserDtoResponse {

    private Long id;
    private String comment;
    private String cart;
    private String favoriteItem;
    private Double amountSpent;

    public ClientDtoResponse(String email, String firstName, String lastName, String phoneNumber, String birthday, String address, String username, Long id, String comment, String cart, String favoriteItem, Double amountSpent) {
        super(email, firstName, lastName, phoneNumber, birthday, address, username);
        this.id = id;
        this.comment = comment;
        this.cart = cart;
        this.favoriteItem = favoriteItem;
        this.amountSpent = amountSpent;
    }
}
