package com.example.FoodieBackend.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.FoodieBackend.Dto.Menuitemrequest;
import com.example.FoodieBackend.Models.MenuItem;
import com.example.FoodieBackend.Repository.MenuItemRepository;

import java.util.List;
 
@Service
@RequiredArgsConstructor
public class Menuservice {
 
    private final MenuItemRepository menuItemRepository;
 
    public List<MenuItem> getAll(String category) {
        if (category != null && !category.isEmpty()) {
            return menuItemRepository.findByCategoryAndAvailableTrue(category);
        }
        return menuItemRepository.findByAvailableTrue();
    }
 
    public MenuItem getById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
    }
 
    public MenuItem create(Menuitemrequest req) {
        MenuItem item = MenuItem.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(req.getCategory())
                .image(req.getImage())
                .vegetarian(req.isVegetarian())
                .available(req.isAvailable())
                .build();
        return menuItemRepository.save(item);
    }
 
    public MenuItem update(Long id, Menuitemrequest req) {
        MenuItem item = getById(id);
        item.setName(req.getName());
        item.setDescription(req.getDescription());
        item.setPrice(req.getPrice());
        item.setCategory(req.getCategory());
        item.setImage(req.getImage());
        item.setVegetarian(req.isVegetarian());
        item.setAvailable(req.isAvailable());
        return menuItemRepository.save(item);
    }
 
    public void delete(Long id) {
        MenuItem item = getById(id);
        // Soft delete - just mark unavailable
        item.setAvailable(false);
        menuItemRepository.save(item);
    }
}