package br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.controller;

import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.entity.Category;
import br.com.aglsrestaurantmanagerserver.restaurantmanagerserver.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> save(@Valid @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.save(category));
    }

    @PutMapping
    public ResponseEntity<Category> update(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(this.categoryService.updade(category));
    }

    @GetMapping
    public ResponseEntity<Page<Category>> list(Pageable pageable) {
        return ResponseEntity.ok(this.categoryService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Category> findByName(@PathParam("name") String name) {
        return ResponseEntity.ok(this.categoryService.findByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
