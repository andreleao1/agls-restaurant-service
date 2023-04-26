package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloseOrderDto {

    private String orderId;
    private PaymentMethod paymentMethod;
    private String paidValue;
}
