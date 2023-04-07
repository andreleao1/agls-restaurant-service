package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ServiceFile <T> {
    T save(MultipartFile multipartFile, T t);
    T updade(T t);
    T findById(Long id);
    Page<T> findAll(Pageable pageable);
    void deleteById(Long id);
}