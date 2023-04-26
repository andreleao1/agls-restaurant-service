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
public class ClosedOrderDto {

    private String orderId;
    private PaymentMethod paymentMethod;
    private String paidValue;
    private String change;
    private Boolean status;
}
