package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.FoodResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.FoodRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.CategoryService;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.util.ObjectBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private S3Service s3Service;

    @Override
    public Food save(MultipartFile multipartFile, Food food) {
        String fileName = s3Service.uploadFile(multipartFile);
        try {
            food.setFileName(fileName);
            return this.foodRepository.save(food);
        } catch (Exception e) {
            log.error("Error to save food, applying rollback...");
            s3Service.deleteFile(fileName);
        }
        return null;
    }

    @Override
    public Food updade(Food food) {
        return null;
    }

    @Override
    public FoodResponseDto findById(Long id) {
        Food foodFound = this.foodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Was not possible found a food with id %d", id)));
        byte[] foodImage = s3Service.getObject(foodFound.getFileName());

        return ObjectBuilder.foodResponseDto(foodFound, foodImage);
    }

    @Override
    public FoodResponseDto findByName(String name) {
        Food foodFound = this.foodRepository.findByName(name).orElseThrow(() -> {
            String message = String.format("Was not possible found a food with name %s", name);
            log.error(message);
            throw new EntityNotFoundException(message);
        });

        byte[] foodImage = s3Service.getObject(foodFound.getFileName());
        return ObjectBuilder.foodResponseDto(foodFound, foodImage);
    }

    @Override
    public Page<FoodResponseDto> findByCategory(Long categoryId, Pageable pageable) {
        log.info(String.format("Finding foods for category %d", categoryId));
        Page<Food> foods = this.foodRepository.findByCategory(categoryId, pageable);
        return buildPageOfFoodResponseDto(foods);
    }

    @Override
    public Page<FoodResponseDto> findByValueRange(BigDecimal initialValue, BigDecimal finalValue, Pageable pageable) {
        log.info(String.format("Finding foods for price between %s and %s", initialValue.toString(), finalValue.toString()));
        Page<Food> foods = this.foodRepository.findByValueRange(initialValue, finalValue, pageable);
        return buildPageOfFoodResponseDto(foods);
    }

    @Override
    public Page<FoodResponseDto> findAll(Pageable pageable) {
        Page<Food> foods = this.foodRepository.findAll(pageable);
        return buildPageOfFoodResponseDto(foods);
    }

    private Page<FoodResponseDto> buildPageOfFoodResponseDto(Page<Food> foods) {
        if(!foods.getContent().isEmpty()) {
            log.debug("Building page of FoodResponseDto");
            List<FoodResponseDto> foodsResponse = new ArrayList<>();
            foods.getContent().forEach(food -> {
                byte[] foodImage = s3Service.getObject(food.getFileName());
                foodsResponse.add(ObjectBuilder.foodResponseDto(food, foodImage));
            });
            return new PageImpl<FoodResponseDto>(foodsResponse);
        }
        return new PageImpl<FoodResponseDto>(new ArrayList<>());
    }

    @Override
    public void deleteById(Long id) {
        try {
            String fileName = getFoodFileName(id);
            if(Objects.isNull(fileName)) {
                String message = String.format("Was not possible found a food with id %d", id);
                log.error(message);
                throw new EntityNotFoundException(message);
            }
            this.foodRepository.deleteById(id);
            s3Service.deleteFile(fileName);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getFoodFileName(Long id) {
        Optional<Food> food = this.foodRepository.findById(id);
        return food.isPresent() ? food.get().getFileName():null;
    }
}
