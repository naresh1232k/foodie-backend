package com.example.FoodieBackend.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
 
import java.time.LocalDateTime;
 
@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String name;
 
    @Column(length = 1000)
    private String description;
 
    @Column(nullable = false)
    private Double price;
 
    private String category;
 
    private String image;
 
    @Builder.Default
    private boolean vegetarian = true;
 
    @Builder.Default
    private boolean available = true;
 
    @Builder.Default
    private Double rating = 4.0;
 
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
 
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
 