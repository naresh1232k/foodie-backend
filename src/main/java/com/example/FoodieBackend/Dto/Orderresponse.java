package com.example.FoodieBackend.Dto;



import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.FoodieBackend.Models.Order;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orderresponse {
    private Long id;
    private String status;
    private Double totalAmount;
    private String deliveryAddress;
    private String paymentId;
    private LocalDateTime createdAt;
    private String userName;
    private String userEmail;
    private List<OrderItemDto> items;
 
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long id;
        private String name;
        private Integer quantity;
        private Double price;
    }
 
    public static Orderresponse from(Order order) {
        return Orderresponse.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .paymentId(order.getPaymentId())
                .createdAt(order.getCreatedAt())
                .userName(order.getUser().getName())
                .userEmail(order.getUser().getEmail())
                .items(order.getItems().stream().map(i -> OrderItemDto.builder()
                        .id(i.getId())
                        .name(i.getMenuItem().getName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}