package com.example.FoodieBackend.Dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Userdto user;
    private String token;
}
 