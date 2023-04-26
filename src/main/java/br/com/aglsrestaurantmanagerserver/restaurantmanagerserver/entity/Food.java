package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(nullable = false)
    private String fileName;

    @PositiveOrZero
    @NotNull
    private BigDecimal value;

    @NotNull
    @ManyToOne
    private Category category;

   // @JsonBackReference
    @ManyToMany(mappedBy = "foods")
    private List<Order> orders;
}
