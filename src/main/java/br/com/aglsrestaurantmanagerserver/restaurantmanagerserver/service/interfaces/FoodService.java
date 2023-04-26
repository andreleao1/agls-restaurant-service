package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.food.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface FoodService extends ServiceFile<Food> {

    FoodResponseDto findById(Long id);

    FoodResponseDto findByName(String name);

    Page<FoodResponseDto> findAll(Pageable pageable);

    Page<FoodResponseDto> findByCategory(Long categoryId, Pageable pageable);

    Page<FoodResponseDto> findByValueRange(BigDecimal initialValue, BigDecimal finalValue, Pageable pageable);

    BigDecimal getValueById(Long id);
}
