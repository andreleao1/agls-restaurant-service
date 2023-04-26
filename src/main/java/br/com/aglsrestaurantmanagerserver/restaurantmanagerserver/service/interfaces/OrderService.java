package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.OrderUpdatedResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;

public interface OrderService {

    Order openingOrder(Order order);

    OrderUpdatedResponseDto closeOrder(CloseOrderDto closeOrderDto);

    OrderUpdatedResponseDto updateOrder(String orderId, Order order);
}
