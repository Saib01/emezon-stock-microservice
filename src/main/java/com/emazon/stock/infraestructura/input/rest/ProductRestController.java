package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.ProductRequest;
import com.emazon.stock.aplicacion.dtos.ProductResponse;
import com.emazon.stock.aplicacion.handler.IProductHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductHandler productHandler;
    @Autowired
    private ObjectMapper objectMapper;
    @Operation(summary = "Add a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Product already exists", content = @Content)
    })
    @PostMapping("/")
    ResponseEntity<Void> saveProduct(@RequestBody ProductRequest productRequest) {
        productHandler.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a Product by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{number}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "number") Long productNumber) {
        return ResponseEntity.ok(productHandler.getProduct(productNumber));
    }

    @Operation(summary = "Get a paginated list of products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })

    @GetMapping
    ResponseEntity<Page<ProductResponse>> getProductsByName(
            @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<ProductResponse> productPage=productHandler.getProductsBySearchTerm(page, size,sortBy, sortDirection);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return ResponseEntity.ok(productPage);
    }
}
