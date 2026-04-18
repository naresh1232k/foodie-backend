package com.example.FoodieBackend.Models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
 
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
 
    @Column(nullable = false)
    private Double totalAmount;
 
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
 
    @Column(length = 500)
    private String deliveryAddress;
 
    private String paymentId;
 
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
 
    public enum OrderStatus {
        PENDING, PREPARING, READY, DELIVERED, CANCELLED
    }
}