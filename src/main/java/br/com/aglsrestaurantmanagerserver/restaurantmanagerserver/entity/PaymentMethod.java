package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH("cash");

    private String paymentMethod;

    private PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
