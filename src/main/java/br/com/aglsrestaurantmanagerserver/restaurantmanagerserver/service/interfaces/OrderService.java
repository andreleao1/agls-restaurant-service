package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;

import java.util.UUID;

public interface OrderService {

    Order openingOrder(Order order);

    CloseOrderResponseDto closeOrder(CloseOrderDto closeOrderDto);

    Order recoverOrderById(String id);
}
