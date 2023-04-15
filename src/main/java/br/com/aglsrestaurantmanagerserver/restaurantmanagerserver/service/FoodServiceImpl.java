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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Base64;

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
    public FoodResponseDto findByCategory(Long categoryId) {
        Food foodFound = this.foodRepository
                .findByCategory(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Was not possible found a food by categoty with id %d", categoryId)));
        foodFound.setCategory(this.categoryService.findById(foodFound.getCategory().getId()));
        byte[] foodImage = s3Service.getObject(foodFound.getFileName());
        return ObjectBuilder.foodResponseDto(foodFound, foodImage);
    }

    @Override
    public FoodResponseDto findByValueBetween(BigDecimal initialValue, BigDecimal finalValue) {
        return null;
    }

    @Override
    public Page<Food> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
