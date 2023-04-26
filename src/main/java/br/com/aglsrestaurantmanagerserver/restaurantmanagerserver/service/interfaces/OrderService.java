package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.ClosedOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.OrderUpdatedResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order openingOrder(Order order);

    Order recoverOrder(String orderId);

    ClosedOrderDto closeOrder(String orderId, CloseOrderDto closeOrderDto);

    OrderUpdatedResponseDto updateOrder(String orderId, Order order);

    Page<Order> listOrders(Pageable pageable);
}
