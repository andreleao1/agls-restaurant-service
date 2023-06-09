package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.CloseOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.ClosedOrderDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order.OrderUpdatedResponseDto;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Food;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Order;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.PaymentMethod;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.repository.OrderRepository;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.FoodService;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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

    @Transactional
    @Override
    public OrderUpdatedResponseDto updateOrder(String orderId, Order order) {
        try {
            Order orderFound = getOrder(orderId);
            log.info(String.format("Updating order %s, order body: %s", order.getId(), order.toString()));
            orderFound.setTotal(updateTotalValue(order.getFoods()));
            orderFound.setFoods(order.getFoods());
            return OrderUpdatedResponseDto.builder().orderId(UUID.fromString(this.orderRepository.save(orderFound).getId())).status(true).build();
        } catch (EntityNotFoundException e) {
            String message = String.format("Unable to find a order with id %s", order.getId().toString());
            log.error(message);
            throw new EntityNotFoundException(message);
        }
        catch (Exception e) {
            log.error("Error to update order " + order.getId());
            throw new RuntimeException(e.getCause());
        }
    }

    private String updateTotalValue(List<Food> foods) {
        if(!foods.isEmpty()) {
            Double currentValue = 0.0;
            for (Food food: foods) {
                currentValue += this.foodService.getValueById(food.getId()).doubleValue();
            }
            return BigDecimal.valueOf(currentValue).toString();
        }
        return BigDecimal.ZERO.toString();
    }

    private Order getOrder(String id) {
        log.info("Applying select in the database");
        Map<String, ?> orderFound = this.orderRepository.findByOrderId(id.toString());

        if(orderFound.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return Order.builder()
                .id(Objects.nonNull(orderFound.get("id")) ? (String) orderFound.get("id"):null)
                .total(Objects.nonNull(orderFound.get("total")) ? (String) orderFound.get("total"):BigDecimal.ZERO.toString())
                .paymentMethod(Objects.nonNull(orderFound.get("payment_method")) ? (PaymentMethod) orderFound.get("payment_method"):null)
                .isPaid((Boolean) orderFound.get("is_paid"))
                .build();
    }

    @Override
    public Page<Order> listOrders(Pageable pageable) {
        Page<Order> ordersPage = this.orderRepository.findAll(pageable);

        if (!ordersPage.getContent().isEmpty()) {
            ordersPage.getContent().forEach(order -> {
                order.getFoods().forEach(food -> {
                    food.setOrders(null);
                });
            });
        }

        return ordersPage;
    }

    @Override
    public Order recoverOrder(String orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> {
            String message = String.format("unable to find order with id %s", orderId);
            log.error(message);
            throw new EntityNotFoundException(message);
        });

        order.getFoods().forEach(food -> {
            food.setOrders(null);
        });

        return order;
    }

    @Override
    public ClosedOrderDto closeOrder(String orderId, CloseOrderDto closeOrderDto) {
        Order order = this.orderRepository.findById(orderId).orElseThrow();

        if (order.isPaid()) {
            String message = String.format("Order %s already paid", order.getId());
            log.warn(message);
            throw new RuntimeException(message);
        }

        if (!isPaidValueEnough(order.getTotal(), closeOrderDto.getPaidValue())) {
            String message = "The paid value is not enough";
            log.error(message);
            throw new RuntimeException(message);
        }

        if (closeOrderDto.getPaymentMethod().equals(PaymentMethod.CASH)) {
            order.setChange(processChange(order.getTotal(), closeOrderDto.getPaidValue()).toString());
        }

        order.setPaymentMethod(closeOrderDto.getPaymentMethod());
        order.setPaidValue(closeOrderDto.getPaidValue());
        order.setPaid(true);

        try {
            this.orderRepository.save(order);
            return  ClosedOrderDto.builder()
                    .orderId(order.getId())
                    .paidValue(order.getPaidValue())
                    .change(order.getChange())
                    .paymentMethod(order.getPaymentMethod())
                    .status(Boolean.TRUE)
                    .build();
        } catch (Exception e) {
            log.error("Error to close order " + order.getId());
            throw new RuntimeException();
        }
    }

    private Boolean isPaidValueEnough(String orderTotalValue, String paidValue) {
        return BigDecimal.valueOf(Double.parseDouble(orderTotalValue)).compareTo(BigDecimal.valueOf(Double.parseDouble(paidValue))) == -1;
    }

    private BigDecimal processChange(String orderValue, String paidValue) {
        return BigDecimal.valueOf(Double.parseDouble(paidValue)).subtract(BigDecimal.valueOf(Double.parseDouble(orderValue)));
    }
}
