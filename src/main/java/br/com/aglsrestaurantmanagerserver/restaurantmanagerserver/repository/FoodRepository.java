package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
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

    @Query(value =
                "select " +
                "id, " +
                "name," +
                " description," +
                " file_name," +
                " value, " +
                "category_id " +
                "from food " +
                " where value between :initialValue and :finalValue", nativeQuery = true )
    Page<Food> findByValueRange(@Param("initialValue")BigDecimal initialValue, @Param("finalValue")BigDecimal finalValue, Pageable pageable);
}
