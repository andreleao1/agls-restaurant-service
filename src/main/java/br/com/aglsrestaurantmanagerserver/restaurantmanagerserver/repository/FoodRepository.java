package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query(value =
            "select " +
            "id, " +
            "name," +
            " description," +
            " file_name," +
            " value, " +
            "category_id " +
            "from food " +
           " where category_id = :categoryId", nativeQuery = true)
    Optional<Food> findByCategory(@Param("categoryId") Long categoryId);
}
