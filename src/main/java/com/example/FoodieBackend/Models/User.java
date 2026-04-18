package com.example.FoodieBackend.Models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String name;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false)
   private String password;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   @Builder.Default
   private Role role = Role.USER;

   @Builder.Default
   private boolean active = true;

   @CreationTimestamp
   @Column(updatable = false)
   private LocalDateTime createdAt;

   public enum Role {
       USER, ADMIN
   }

   public boolean isActive() {
	    return this.active;
	}
}
