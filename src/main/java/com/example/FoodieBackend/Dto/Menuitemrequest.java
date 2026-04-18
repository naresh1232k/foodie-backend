package com.example.FoodieBackend.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;
 
@Data
public class Menuitemrequest {
    @NotBlank
    private String name;
 
    private String description;
 
    @NotNull
    @Positive
    private Double price;
 
    private String category;
 
    private String image;
 
    private boolean vegetarian = true;
 
    private boolean available = true;
}
 