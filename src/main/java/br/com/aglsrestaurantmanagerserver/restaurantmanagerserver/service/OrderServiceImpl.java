package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.OrderRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order openingOrder(Order order) {
        return this.orderRepository.save(order);
    }
}
