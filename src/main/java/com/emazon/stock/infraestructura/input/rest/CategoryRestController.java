package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {
        private final ICategoryHandler categoryHandler;

        @Operation(summary = "Add a new category")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Category created", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Category already exists", content = @Content)
        })
        @PostMapping("/")
        ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest) {
                categoryHandler.saveCategory(categoryRequest);
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @Operation(summary = "Get a paginated list of categories")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of categories", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
        })
        @GetMapping
        ResponseEntity<Page<CategoryResponse>> getCategoriesByName(
                        @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size) {
                return ResponseEntity.ok(categoryHandler.getCategoriesByName(page, size, sortDirection));
        }

        @Operation(summary = "Get a category by number")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Category found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
        })
        @GetMapping("/{number}")
        ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = "number") Long categoryNumber) {
                return ResponseEntity.ok(categoryHandler.getCategory(categoryNumber));
        }
}
