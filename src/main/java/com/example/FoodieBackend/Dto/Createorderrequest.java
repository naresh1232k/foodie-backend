package com.example.FoodieBackend.Dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;
 
@Data
public class Createorderrequest {
    @NotNull
    private List<OrderItemRequest> items;
 
    @NotBlank
    private String deliveryAddress;
 
    @NotNull	
    @Positive
    private Double totalAmount;
 
    private String paymentId;
 
    @Data
    public static class OrderItemRequest {
        private Long menuItemId;
        private Integer quantity;
        private Double price;
    }
}