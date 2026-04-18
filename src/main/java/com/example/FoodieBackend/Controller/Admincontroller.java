package com.example.FoodieBackend.Controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.FoodieBackend.Dto.Userdto;
import com.example.FoodieBackend.Service.Userservice;

import java.util.List;
 
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class Admincontroller {
 
    private final Userservice userService;
 
    @GetMapping("/users")
    public ResponseEntity<List<Userdto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
 
    @PutMapping("/users/{id}/toggle")
    public ResponseEntity<Userdto> toggleUserStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUserStatus(id));
    }
}
