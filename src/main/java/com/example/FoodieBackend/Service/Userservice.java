package com.example.FoodieBackend.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.FoodieBackend.Dto.Userdto;
import com.example.FoodieBackend.Models.User;
import com.example.FoodieBackend.Repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
 
@Service
@RequiredArgsConstructor
public class Userservice {
 
    private final UserRepository userRepository;
 
    public List<Userdto> getAllUsers() {
        return userRepository.findAll().stream().map(Userdto::from) .collect(Collectors.toList());
    }
 
    public Userdto toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
 
        if (user.getRole() == User.Role.ADMIN) {
            throw new RuntimeException("Cannot deactivate admin accounts");
        }
 
        user.setActive(!user.isActive());
        return Userdto.from(userRepository.save(user));
    }
}
 