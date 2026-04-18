package com.example.FoodieBackend.Dto;

import lombok.*;

import java.time.LocalDateTime;

import com.example.FoodieBackend.Models.User;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Userdto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean active;
    private LocalDateTime createdAt;
 
    public static Userdto from(User u) {
        return Userdto.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole().name())
                .active(u.isActive())
                .createdAt(u.getCreatedAt())
                .build();
    }
}