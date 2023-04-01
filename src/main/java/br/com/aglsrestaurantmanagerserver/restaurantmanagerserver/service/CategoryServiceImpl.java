package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.CategoryRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        if (this.categoryRepository.existsByName(category.getName())) {
            String message = String.format("Category %s already exist.", category.getName());
            LOGGER.info(message);
            throw new EntityExistsException(message);
        }

        return this.categoryRepository.save(category);
    }

    @Override
    public Category updade(Category category) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        try {
            this.categoryRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error to delete category with id: " + id);
        }
    }
}
