package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.request.BrandRequest;
import com.emazon.stock.aplicacion.dtos.response.BrandResponse;
import com.emazon.stock.aplicacion.handler.IBrandHandler;
import com.emazon.stock.dominio.modelo.PageStock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {
        private final IBrandHandler brandHandler;

        @Operation(summary = "Add a new brand")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Brand created", content = @Content),
                        @ApiResponse(responseCode = "409", description = "Brand already exists", content = @Content)
        })
        @PostMapping("/")
        ResponseEntity<Void> saveBrand(@RequestBody BrandRequest brandRequest) {
                brandHandler.saveBrand(brandRequest);
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        @Operation(summary = "Get a paginated list of brands")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "List of brands", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
        })
        @GetMapping
        ResponseEntity<PageStock<BrandResponse>> getBrandsByName(
                @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
                @RequestParam(name = "page", defaultValue = "0") int page,
                @RequestParam(name = "size", defaultValue = "10") int size) {
                return ResponseEntity.ok(brandHandler.getBrandsByName(page, size, sortDirection));
        }
        @Operation(summary = "Get a brand by number")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Brand found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
        })
        @GetMapping("/{number}")
        ResponseEntity<BrandResponse> getBrand(@PathVariable(name = "number") Long brandNumber) {
                return ResponseEntity.ok(brandHandler.getBrand(brandNumber));
        }
}
