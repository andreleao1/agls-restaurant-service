package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.FoodRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public Food save(MultipartFile multipartFile, Food food) {
        String fileName = s3Service.uploadFile(multipartFile);
        return this.foodRepository.save(food);
    }

    @Override
    public Food updade(Food food) {
        return null;
    }

    @Override
    public Food findById(Long id) {
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
