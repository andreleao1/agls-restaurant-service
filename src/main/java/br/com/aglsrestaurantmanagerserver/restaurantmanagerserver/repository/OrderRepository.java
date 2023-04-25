package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

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

    @Query(value =
            "update restaurant_order " +
            "set " +
            "total = :total " +
            "where " +
            "id = :id", nativeQuery = true)
    Void updateOrder(@Param("total") String total, @Param("id") String id);

}
