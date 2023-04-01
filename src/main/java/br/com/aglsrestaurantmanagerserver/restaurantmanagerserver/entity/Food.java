package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    @Size(max = 100)
    private String description;

    @PositiveOrZero
    @NotNull
    private BigDecimal value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cateogry_id")
    private Category category;
}
