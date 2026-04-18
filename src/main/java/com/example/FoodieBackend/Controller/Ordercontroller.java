package com.example.FoodieBackend.Controller;

import com.example.FoodieBackend.Dto.*;
import com.example.FoodieBackend.Models.User;
import com.example.FoodieBackend.Repository.UserRepository;
import com.example.FoodieBackend.Service.Orderservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class Ordercontroller {

    private final Orderservice orderService;
    private final UserRepository userRepository;

    // ✅ User: place order
    @PostMapping
    public ResponseEntity<Orderresponse> createOrder(@Valid @RequestBody Createorderrequest req, Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderService.createOrder(req, user));
    }

    
    
    // ✅ User: get own orders
    
    @GetMapping("/my")
    public ResponseEntity<List<Orderresponse>> myOrders(Authentication auth) {

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(orderService.getMyOrders(user));
    }

    
    
    
    // ✅ Admin: all orders
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Orderresponse>> allOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ Admin: update status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Orderresponse> updateStatus(
            @PathVariable Long id,
            @RequestBody Statusupdaterequest req) {

        return ResponseEntity.ok(orderService.updateStatus(id, req.getStatus()));
    }
}