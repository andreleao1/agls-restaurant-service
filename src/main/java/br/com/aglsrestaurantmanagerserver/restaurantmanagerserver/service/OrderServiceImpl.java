package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.OrderRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodService foodService;


    @Override
    public Order openingOrder(Order order) {
        order.setTotal(updateTotalValue(order.getFoods()));
        return this.orderRepository.save(order);
    }

    @Override
    public CloseOrderResponseDto closeOrder(CloseOrderDto closeOrderDto) {
        Order order = this.orderRepository.findById(UUID.fromString(closeOrderDto.getOrderId())).orElseThrow();
        order.setPaymentMethod(closeOrderDto.getPaymentMethod());
        order.setPaid(true);

        try {
            this.orderRepository.save(order);
            return  CloseOrderResponseDto.builder().status(true).orderId(closeOrderDto.getOrderId()).build();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private BigDecimal updateTotalValue(List<Food> foods) {
        if(!foods.isEmpty()) {
            Double currentValue = 0.0;
            for (Food food: foods) {
                currentValue += this.foodService.getValueById(food.getId()).doubleValue();
            }
            return BigDecimal.valueOf(currentValue);
        }
        return BigDecimal.ZERO;
    }



    @Override
    public Order recoverOrderById(String id) {
        List<String> foods = this.orderRepository.findByOrderId(id);
        return null;
    }
}
