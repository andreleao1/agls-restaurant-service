package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.util;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;

import java.math.BigDecimal;

public class ObjectBuilder {

    public static Food foodBuilder(String name, String description, String value, String categoryId) {
        return Food.builder()
                .name(name)
                .description(description.isEmpty() || description.isBlank() ? null:description)
                .value(BigDecimal.valueOf(Double.parseDouble(value)))
                .category(Category.builder().id(Long.parseLong(categoryId)).build())
                .build();
    }

    public static FoodResponseDto foodResponseDto(Food food, byte[] foodImage) {
        return FoodResponseDto.builder()
                .id(food.getId())
                .name(food.getName())
                .description(food.getDescription())
                .value(food.getValue().toString())
                .category(food.getCategory())
                .foodImage(foodImage)
                .build();
    }
}
