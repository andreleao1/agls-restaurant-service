package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save a new category")
    public void mustSaveACategoryWhenGivenAValidCategory() {
        Long categoryId = 1L;
        Category category = Category.builder().name("TEST").build();
        when(this.categoryRepository.existsByName(category.getName())).thenReturn(false);
        when(this.categoryRepository.save(Mockito.any(Category.class))).thenReturn(Category.builder().id(categoryId).name(category.getName()).build());

        Category savedCategory = this.categoryService.save(category);

        assertThat(savedCategory.getId()).isEqualTo(categoryId);
        verify(this.categoryRepository, times(1)).save(Mockito.any(Category.class));
    }

    @Test
    @DisplayName("Throws an error to save a category with exists name")
    public void mustThrowAnEntityExistsExceptionWhenTrySaveACategoryWithExistName() {
        Category category = Category.builder().name("TEST").build();
        when(this.categoryRepository.existsByName(category.getName())).thenReturn(true);

        assertThatThrownBy(() -> this.categoryService.save(category))
                .isInstanceOf(EntityExistsException.class);
    }
}
