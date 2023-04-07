package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.controller;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import jakarta.validation.Valid;
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
        Food food = Food.builder()
                .name(name)
                .description(description)
                .value(BigDecimal.valueOf(Double.parseDouble(value)))
                .category(Category.builder().id(Long.parseLong(categoryId)).build())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(this.foodService.save(file, food));
    }
}
