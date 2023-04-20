package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}
