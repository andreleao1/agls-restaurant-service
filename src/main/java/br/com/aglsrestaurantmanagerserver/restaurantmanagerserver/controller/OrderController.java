package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.controller;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.OrderService;
import com.amazonaws.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> openingOrder(@RequestBody @Valid Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.openingOrder(order));
    }
}
