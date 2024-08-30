package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.BrandRequest;
import com.emazon.stock.aplicacion.dtos.BrandResponse;
import com.emazon.stock.aplicacion.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        ResponseEntity<Void> saveCategory(@RequestBody BrandRequest brandRequest) {
                brandHandler.saveBrand(brandRequest);
                return ResponseEntity.status(HttpStatus.CREATED).build();
        }


        @Operation(summary = "Get a brand by number")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Brand found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
        })
        @GetMapping("/{number}")
        ResponseEntity<BrandResponse> getCategory(@PathVariable(name = "number") Long brandNumber) {
                return ResponseEntity.ok(brandHandler.getBrand(brandNumber));
        }
}
