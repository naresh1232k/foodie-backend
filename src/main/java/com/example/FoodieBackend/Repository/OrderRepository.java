package com.example.FoodieBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FoodieBackend.Models.Order;
import com.example.FoodieBackend.Models.User;

import java.util.List;
 
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findAllByOrderByCreatedAtDesc();
}
 