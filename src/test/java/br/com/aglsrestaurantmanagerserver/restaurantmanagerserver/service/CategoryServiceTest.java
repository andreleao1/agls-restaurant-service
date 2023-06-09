package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

    @Test
    @DisplayName("Update a category")
    public void mustUpdateACategoryWhenGivenAValidCategory() {
        Long categoryId = 1l;
        Category category = Category.builder().id(categoryId).name("TEST").build();
        Category newCategory = Category.builder().id(categoryId).name("TEST_2").build();

        when(this.categoryRepository.existsByName(category.getName())).thenReturn(false);
        when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(this.categoryRepository.save(Mockito.any(Category.class))).thenReturn(Category.builder().id(categoryId).name(newCategory.getName()).build());

        Category categoryUpdated = this.categoryService.save(category);

        assertThat(categoryUpdated.getId()).isEqualTo(categoryId);
        assertThat(categoryUpdated.getName()).isEqualTo(newCategory.getName());
        verify(this.categoryRepository, times(1)).save(Mockito.any(Category.class));
    }

    @Test
    @DisplayName("Find one category by id")
    public void mustReturnACategoryWhenOneValidIdIsProvided() {
        Category category = Category.builder().id(1l).name("TEST").build();

        when(this.categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Category categoryFound = this.categoryService.findById(category.getId());

        verify(this.categoryRepository, times(1)).findById(category.getId());
        assertThat(categoryFound).isEqualTo(category);
    }

    @Test
    @DisplayName("Throws an error to try find a category nonexistent")
    public void mustThrowAnEntityNotFoundExceptionWhenTryFindACategoryWithNonexistentId() {
        Category category = Category.builder().id(1l).name("TEST").build();
        when(this.categoryRepository.findById(category.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.categoryService.findById(category.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Find one category by name")
    public void mustReturnACategoryWhenOneValidNameIsProvided() {
        String categoryName = "TEST";
        Category category = Category.builder().id(1l).name(categoryName).build();

        when(this.categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        Category categoryFound = this.categoryService.findByName(categoryName);

        verify(this.categoryRepository, times(1)).findByName(categoryName);
        assertThat(categoryFound).isEqualTo(category);
    }

    @Test
    @DisplayName("delete by id")
    public void mustDeleteACategoryWhenOneValidIdIsProvided() {
        Long categoryId = 1l;

        when(this.categoryRepository.findById(categoryId)).thenReturn(Optional.of(Category.builder().build()));

        this.categoryService.deleteById(categoryId);

        verify(this.categoryRepository, times(1)).deleteById(categoryId);
    }

}
