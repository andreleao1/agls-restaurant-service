package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "food_id")
    private Long foodId;
    @Column(name = "order_id")
    private UUID orderId;
}
