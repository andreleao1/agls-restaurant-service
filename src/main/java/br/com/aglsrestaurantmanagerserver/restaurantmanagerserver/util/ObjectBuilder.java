package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.util;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.food.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;

import java.math.BigDecimal;
import java.util.Objects;

public class ObjectBuilder {

    public static Food foodBuilder(String name, String description, String value, String categoryId) {
        return Food.builder()
                .name(name)
                .description(description.isEmpty() || description.isBlank() ? null:description)
                .value(BigDecimal.valueOf(Double.parseDouble(value)))
                .category(Category.builder().id(Long.parseLong(categoryId)).build())
                .build();
    }

    public static Food foodBuilder(Long id, String name, String description, String value, String categoryId) {
        return Food.builder()
                .id(id)
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
                .description(Objects.nonNull(food.getDescription()) ? food.getDescription():null)
                .value(food.getValue().toString())
                .category(Category.builder().id(food.getCategory().getId()).name(food.getCategory().getName()).build())
                .foodImage(foodImage)
                .build();
    }
}
