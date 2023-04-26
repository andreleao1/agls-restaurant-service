package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(value = "select " +
            "id, " +
            "created_at, " +
            "updated_at, " +
            "total, " +
            "payment_method, " +
            "is_paid " +
            "from restaurant_order " +
            "where id = :id", nativeQuery = true)
    Map<String, ?> findByOrderId(@Param("id") String id);
}
