package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final ICategoryHandler categoryHandler;
    @PostMapping
    ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest){
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    ResponseEntity<Page<CategoryResponse>> getCategories(
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(categoryHandler.getCategories(sortDirection,page,size));
    }
    @GetMapping("/{number}")
    ResponseEntity<CategoryResponse> getCategory(@PathVariable(name="number") Long categoryNumber){
        return ResponseEntity.ok(categoryHandler.getCategory(categoryNumber));
    }
}
