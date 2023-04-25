package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.controller;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.OrderUpdatedResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> openingOrder(@RequestBody @Valid Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.openingOrder(order));
    }

    @PutMapping("/update")
    public ResponseEntity<OrderUpdatedResponseDto> updateOrder(@PathParam("orderId")String orderId, @RequestBody Order order) {
        order.setId(UUID.fromString(orderId));
        return ResponseEntity.ok(this.orderService.updateOrder(order));
    }

    @PutMapping("/close")
    public ResponseEntity<OrderUpdatedResponseDto> closeOrder(@RequestBody CloseOrderDto closeOrderDto) {
        return ResponseEntity.ok(this.orderService.closeOrder(closeOrderDto));
    }

    @GetMapping("/search")
    public ResponseEntity<Order> recoverOrder(@PathParam("orderId")String orderId) {
        return ResponseEntity.ok(this.orderService.recoverOrderById(orderId));
    }
}
