package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;

import java.math.BigDecimal;

public interface FoodService extends ServiceFile<Food> {
    FoodResponseDto findById(Long id);

    FoodResponseDto findByCategory(Long categoryId);

    FoodResponseDto findByValueBetween(BigDecimal initialValue, BigDecimal finalValue);
}
