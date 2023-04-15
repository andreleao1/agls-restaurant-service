package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.controller;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.util.ObjectBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping
    public ResponseEntity<Food> save(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("value") String value, @RequestParam("category") String categoryId) {
        Food food = ObjectBuilder.foodBuilder(name, description, value, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.foodService.save(file, food));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.foodService.findById(id));
    }

    @GetMapping("/find-by-category/{id}")
    public ResponseEntity<FoodResponseDto> findByCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(this.foodService.findByCategory(categoryId));
    }
}
