package com.example.FoodieBackend.Config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.FoodieBackend.Models.MenuItem;
import com.example.FoodieBackend.Models.User;
import com.example.FoodieBackend.Repository.MenuItemRepository;
import com.example.FoodieBackend.Repository.UserRepository;

import java.util.List;
 
@Component
@RequiredArgsConstructor
@Slf4j
public class Dataseeder implements CommandLineRunner {
 
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final PasswordEncoder passwordEncoder;
 
    @Override
    public void run(String... args) {
        seedAdmin();
        seedMenu();
    }
 
    private void seedAdmin() {
        if (userRepository.existsByEmail("admin@fodie.com")) return;
 
        
        User admin = User.builder()
                .name("Admin")
                .email("admin@fodie.com")
                .password(passwordEncoder.encode("admin123"))
                .role(User.Role.ADMIN)
                .active(true)
                .build();
 
        userRepository.save(admin);
        log.info("✅ Admin seeded → email: admin@fodie.com | password: admin123");
    }
 
    private void seedMenu() {
        if (menuItemRepository.count() > 0) return;
 
        List<MenuItem> items = List.of(
        	    MenuItem.builder().name("Green Toast with Eggs")
        	        .description("Avocado toast with poached eggs and fresh baby spinach leaves")
        	        .price(220.0).category("Breakfast").vegetarian(false).rating(4.5)
        	        .image("https://images.unsplash.com/photo-1525351484163-7529414344d8?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Buddha Bowl")
        	        .description("Nourishing bowl packed with quinoa, roasted vegetables, chickpeas and tahini")
        	        .price(280.0).category("Bowls").vegetarian(true).rating(5.0)
        	        .image("https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Garden Salad")
        	        .description("Fresh garden vegetables with herb vinaigrette dressing and seed croutons")
        	        .price(180.0).category("Salads").vegetarian(true).rating(4.0)
        	        .image("https://images.unsplash.com/photo-1540420773420-3366772f4999?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Lentil Soup")
        	        .description("Hearty red lentil soup with cumin, coriander and fresh lemon")
        	        .price(150.0).category("Soups").vegetarian(true).rating(4.5)
        	        .image("https://images.unsplash.com/photo-1547592166-23ac45744acd?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Mushroom Risotto")
        	        .description("Creamy arborio rice with wild mushrooms, truffle oil and parmesan")
        	        .price(320.0).category("Bowls").vegetarian(true).rating(4.8)
        	        .image("https://images.unsplash.com/photo-1476124369491-e7addf5db371?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Chia Pudding")
        	        .description("Coconut chia pudding layered with mango, kiwi and fresh berries")
        	        .price(140.0).category("Desserts").vegetarian(true).rating(4.2)
        	        .image("https://images.unsplash.com/photo-1546039907-7b9836ab4c23?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Green Detox Smoothie")
        	        .description("Spinach, banana, almond milk, ginger and chia seeds")
        	        .price(120.0).category("Drinks").vegetarian(true).rating(4.7)
        	        .image("https://images.unsplash.com/photo-1611070032073-1aa5e5e99d20?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Acai Bowl")
        	        .description("Acai blend topped with granola, banana, coconut flakes and honey")
        	        .price(260.0).category("Breakfast").vegetarian(true).rating(4.9)
        	        .image("https://images.unsplash.com/photo-1590301157890-4810ed352733?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Cauliflower Steak")
        	        .description("Roasted cauliflower steak with chimichurri sauce and microgreens")
        	        .price(300.0).category("Salads").vegetarian(true).rating(4.3)
        	        .image("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&q=80")
        	        .build(),

        	    MenuItem.builder().name("Coconut Curry")
        	        .description("Fragrant Thai green curry with tofu, vegetables and jasmine rice")
        	        .price(250.0).category("Bowls").vegetarian(true).rating(4.6)
        	        .image("https://images.unsplash.com/photo-1455619452474-d2be8b1e70cd?w=400&q=80")
        	        .build()
        	);
        menuItemRepository.saveAll(items);
        log.info("✅ Menu seeded with {} items", items.size());
    }
}
