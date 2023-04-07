package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;

public interface CategoryService extends DefaultService<Category> {
    Category findByName(String name);
}
