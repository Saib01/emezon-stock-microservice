package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.Message;
import com.emazon.stock.aplicacion.dtos.brand.BrandRequest;
import com.emazon.stock.aplicacion.dtos.brand.BrandResponse;
import com.emazon.stock.aplicacion.handler.IBrandHandler;
import com.emazon.stock.dominio.utils.PageStock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.infraestructura.util.InfraestructureRestControllerConstants.*;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.NUMBER;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.SIZE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandRestController {

    private final IBrandHandler brandHandler;

    @Operation(summary = SUMMARY_ADD_A_NEW_BRAND)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_CREATED, description = BRAND_CREATED, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = BRAND_ALREADY_EXISTS, content = @Content)
    })
    @PostMapping("/")
    ResponseEntity<Message> saveBrand(@RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(BRAND_CREATED));
    }

    @Operation(summary = SUMMARY_GET_A_PAGINATED_LIST_OF_BRANDS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = LIST_OF_BRANDS, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageStock.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_REQUEST, content = @Content)
    })
    @GetMapping
    ResponseEntity<PageStock<BrandResponse>> getBrandsByName(
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(brandHandler.getBrandsByName(page, size, sortDirection));
    }

    @Operation(summary = SUMMARY_GET_A_BRAND_BY_NUMBER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = BRAND_FOUND, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BrandResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = BRAND_NOT_FOUND, content = @Content)
    })
    @GetMapping("/{number}")
    ResponseEntity<BrandResponse> getBrand(@PathVariable(name = NUMBER) Long brandNumber) {
        return ResponseEntity.ok(brandHandler.getBrand(brandNumber));
    }
}
