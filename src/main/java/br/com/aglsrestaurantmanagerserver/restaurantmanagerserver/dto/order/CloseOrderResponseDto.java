package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloseOrderResponseDto {

    private Boolean status;
    private String orderId;
}
