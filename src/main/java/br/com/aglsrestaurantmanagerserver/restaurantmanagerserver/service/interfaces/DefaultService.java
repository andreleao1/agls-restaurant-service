package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefaultService <T> {
    T save(T t);
    T updade(T t);
    T findById(Long id);
    Page<T> findAll(Pageable pageable);
    void deleteById(Long id);
}
