package com.example.FoodieBackend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FoodieBackend.Models.MenuItem;

import java.util.List;
 
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailableTrue();
    List<MenuItem> findByCategoryAndAvailableTrue(String category);
    List<MenuItem> findByCategory(String category);
}
 