package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.CategoryRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    private void validation(Category category) {
        nameAlreadyExist(category.getName());
    }
    private void nameAlreadyExist(String name) {
        if (this.categoryRepository.existsByName(name)) {
            String message = String.format("Category %s already exist.", name);
            LOGGER.info(message);
            throw new EntityExistsException(message);
        }
    }

    @Override
    public Category save(Category category) {
        validation(category);
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updade(Category category) {
        validation(category);
        Category categoryFound = findById(category.getId());
        BeanUtils.copyProperties(category,categoryFound,"id");
        return this.categoryRepository.save(categoryFound);
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
            findById(id);
            this.categoryRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Error to delete category with id: " + id);
        }
    }

    @Override
    public Category findByName(String name) {
        return this.categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException());
    }
}
