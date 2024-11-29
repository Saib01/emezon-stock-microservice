package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.Response;
import com.emazon.stock.aplicacion.dtos.ShoppingCartListRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductRequest;
import com.emazon.stock.aplicacion.dtos.product.ProductResponse;
import com.emazon.stock.aplicacion.handler.IProductHandler;
import com.emazon.stock.dominio.utils.PageStock;
import com.emazon.stock.infraestructura.output.jpa.mapper.ProductEntityMapper;
import com.emazon.stock.infraestructura.output.jpa.repository.IProductRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emazon.stock.dominio.utils.Direction.ASC;
import static com.emazon.stock.infraestructura.util.InfraestructureRestControllerConstants.*;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.NUMBER;
import static com.emazon.stock.infraestructura.util.InfrastructureConstants.SIZE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestController {
    private final IProductHandler productHandler;
    private final ObjectMapper objectMapper;
    private final IProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Operation(summary = SUMMARY_ADD_A_NEW_PRODUCT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_CREATED, description = RESPONSE_DESCRIPTION_PRODUCT_CREATED, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = CONFLICT_PRODUCT_NOT_CREATED_DESCRIPTION, content = @Content)
    })
    @PostMapping("/")
    ResponseEntity<Response> saveProduct(@RequestBody ProductRequest productRequest) {
        productHandler.saveProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(RESPONSE_DESCRIPTION_PRODUCT_CREATED));
    }

    @Operation(summary = SUMMARY_GET_A_PRODUCT_BY_NUMBER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = PRODUCT_FOUND, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = PRODUCT_NOT_FOUND, content = @Content)
    })
    @GetMapping("/{number}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable(name = NUMBER) Long productNumber) {
        return ResponseEntity.ok(productHandler.getProduct(productNumber));
    }

    @Operation(summary = SUMMARY_GET_A_PAGINATED_LIST_OF_PRODUCTS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = LIST_OF_PRODUCTS, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageStock.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_REQUEST, content = @Content)
    })

    @GetMapping
    ResponseEntity<PageStock<ProductResponse>> getProductsByName(
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE ) int size) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return ResponseEntity.ok(productHandler.getProductsBySearchTerm(page, size, sortBy, sortDirection));
    }

    @Operation(summary = SUMMARY_INCREASE_SUPPLY_STOCK, description = INCREASES_THE_STOCK_OF_A_SUPPLY_BY_A_GIVEN_INCREMENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STOCK_UPDATED_SUCCESSFULLY),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST , description = INVALID_REQUEST)
    })
    @PutMapping("/{id}/add-supply")
    public ResponseEntity<Void> addSupply(@PathVariable Long id, @RequestParam Integer increment) {
        productHandler.addSupply(id, increment);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = SUMMARY_VALIDATE_THE_LIMIT_OF_PRODUCTS_PER_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = VALIDATION_COMPLETED_SUCCESSFULLY, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_INPUT_DATA, content = @Content)
    })
    @GetMapping("/validate-category-limit")
    public ResponseEntity<Boolean> validateMaxThreeProductPerCategory(
            @RequestParam List<Long> listIdsProducts) {
        return ResponseEntity.ok(
                this.productHandler.validateMaxProductPerCategory(listIdsProducts)
        );
    }

    @Operation(summary = GET_PAGINATED_LIST_OF_PRODUCTS_IN_SHOPPING_CART,
            description = RETRIEVE_A_PAGINATED_LIST_OF_PRODUCTS_IN_THE_SHOPPING_CART_BASED_ON_THE_PROVIDED_LIST_REQUEST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = SUCCESSFULLY_RETRIEVED_PAGINATED_PRODUCT_LIST,
                    content = @Content(schema = @Schema(implementation = PageStock.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_REQUEST_DATA, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER, description = INTERNAL_SERVER_ERROR1, content = @Content)
    })
    @PostMapping("/product-list")
    public ResponseEntity<PageStock<ProductResponse>> getPaginatedProductsInShoppingCart(
            @RequestBody ShoppingCartListRequest shoppingCartListRequest,
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE ) int size) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return ResponseEntity.ok(productHandler.getPaginatedProductsInShoppingCart(page, size, sortDirection,shoppingCartListRequest));
    }

    @Operation(summary = REDUCE_STOCK_AFTER_PURCHASE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STOCK_REDUCED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_PRODUCT_REQUEST_DATA, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER, description = INTERNAL_SERVER_ERROR_WHILE_REDUCING_STOCK, content = @Content)
    })
    @PutMapping("/reduce-stock")
    public ResponseEntity<List<ProductResponse>> reduceStockAfterPurchase(@RequestBody List<ProductRequest> productRequestList) {

        return ResponseEntity.ok(productHandler.reduceStockAfterPurchase(productRequestList));
    }

    @Operation(summary = RESTORE_STOCK_TO_PREVIOUS_STATE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = STOCK_RESTORED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_PRODUCT_REQUEST_DATA1, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER, description = INTERNAL_SERVER_ERROR_WHILE_RESTORING_STOCK, content = @Content)
    })
    @PutMapping("/restore-stock-fallback")
    public ResponseEntity<Response> restoreStockToPreviousStateFallback(@RequestBody List<ProductRequest> productRequestList) {
        productHandler.restoreStockToPreviousState(productRequestList);
        return ResponseEntity.ok(new Response(STOCK_RESTORATION_COMPLETED));
    }

    @Operation(summary = VALIDATE_IF_THE_PRODUCT_NAME_IS_AVAILABLE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = PRODUCT_NAME_IS_AVAILABLE,
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_PRODUCT_NAME_FORMAT, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_INTERNAL_SERVER, description = INTERNAL_SERVER_ERROR2, content = @Content)
    })
    @PostMapping("/validate-name")
    ResponseEntity<Boolean> isProductNameAvailable(@RequestBody String productName) {
        return ResponseEntity.ok( productHandler.isProductNameAvailable(productName));
    }
}
