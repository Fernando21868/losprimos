package com.example.backend.dto.response;

import com.example.backend.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientDtoResponse extends UserDtoResponse {

    private Long id;
    private String comment;
    private String cart;
    private String favoriteItem;
    private Double amountSpent;

    public ClientDtoResponse(String email, String firstName, String lastName, String phoneNumber, String birthday, String address, String username, List<Role> roles, Long id, String comment, String cart, String favoriteItem, Double amountSpent) {
        super(email, firstName, lastName, phoneNumber, birthday, address, username, roles);
        this.id = id;
        this.comment = comment;
        this.cart = cart;
        this.favoriteItem = favoriteItem;
        this.amountSpent = amountSpent;
    }
}
