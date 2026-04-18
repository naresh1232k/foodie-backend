package com.example.FoodieBackend.Controller;

import com.example.FoodieBackend.Models.MenuItem;
import com.example.FoodieBackend.Repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*")
public class Menucontroller {

    @Autowired
    private MenuItemRepository menuRepository;

    // ✅ GET ALL
    @GetMapping
    public List<MenuItem> getAll() {
        return menuRepository.findAll();
    }

    // ✅ CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String category,
            @RequestParam Boolean vegetarian,
            @RequestParam(required = false) MultipartFile image
    ) {

        String imageUrl = saveImage(image);

        MenuItem menu = MenuItem.builder()
                .name(name)
                .description(description)
                .price(price)
                .category(category)
                .vegetarian(vegetarian)
                .image(imageUrl)
                .build();

        return ResponseEntity.ok(menuRepository.save(menu));
    }

    // ✅ UPDATE
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String category,
            @RequestParam Boolean vegetarian,
            @RequestParam(required = false) MultipartFile image
    ) {

        MenuItem menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setCategory(category);
        menu.setVegetarian(vegetarian);

        // ✅ update image only if new file selected
        if (image != null && !image.isEmpty()) {
            menu.setImage(saveImage(image));
        }

        return ResponseEntity.ok(menuRepository.save(menu));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
    

    @GetMapping("/fix-images")
    public void fixImages() {
        List<MenuItem> items = menuRepository.findAll();

        for (MenuItem item : items) {
            if (item.getImage() != null && item.getImage().contains("/uploads/")) {
                item.setImage(item.getImage().replace("/uploads/", "/imgs/"));
                menuRepository.save(item);
            }
        }
    }
    
    private String saveImage(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                System.out.println("❌ EMPTY FILE RECEIVED");
                return null;
            }

            String folder = System.getProperty("user.dir") + "/uploads/imgs/";
            Files.createDirectories(Paths.get(folder));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(folder + fileName);

            Files.write(path, file.getBytes());

            System.out.println("✔ SAVED: " + path.toAbsolutePath());

            return "/imgs/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Upload failed");
        }
    }
}