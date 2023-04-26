package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdatedResponseDto {

    private Boolean status;
    private UUID orderId;
}
