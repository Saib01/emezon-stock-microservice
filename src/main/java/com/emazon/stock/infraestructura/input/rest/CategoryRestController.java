package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ResponseEntity<List<CategoryResponse>> getAllCategory(){

        return ResponseEntity.ok(categoryHandler.getAllCategory());
    }
    @GetMapping("/{number}")
    ResponseEntity<CategoryResponse> getCategory(@PathVariable(name="number") Long categoryNumber){
        return ResponseEntity.ok(categoryHandler.getCategory(categoryNumber));
    }
}
