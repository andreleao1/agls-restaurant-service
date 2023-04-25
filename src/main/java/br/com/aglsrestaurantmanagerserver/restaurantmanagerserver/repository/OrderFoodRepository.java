package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderFoodRepository extends JpaRepository<OrderFood, UUID> {
    @Query(value = "delete from order_food where order_id = :orderId", nativeQuery = true)
    Void deleteByOrderId(@Param("orderId") String orderId);
}
