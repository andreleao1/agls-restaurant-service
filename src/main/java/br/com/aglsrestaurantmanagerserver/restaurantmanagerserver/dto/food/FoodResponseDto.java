package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.dto.food;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponseDto {

    private Long id;
    private String name;
    private String description;
    private String value;
    private Category category;
    private byte[] foodImage;
}
