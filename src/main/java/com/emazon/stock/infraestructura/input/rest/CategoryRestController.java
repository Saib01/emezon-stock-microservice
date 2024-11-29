package com.emazon.stock.infraestructura.input.rest;

import com.emazon.stock.aplicacion.dtos.Response;
import com.emazon.stock.aplicacion.dtos.category.CategoryRequest;
import com.emazon.stock.aplicacion.dtos.category.CategoryResponse;
import com.emazon.stock.aplicacion.handler.ICategoryHandler;
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
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;

    @Operation(summary = SUMMARY_ADD_A_NEW_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_CREATED, description = CATEGORY_CREATED, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = CATEGORY_ALREADY_EXISTS, content = @Content)
    })
    @PostMapping("/")
    ResponseEntity<Response> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response(CATEGORY_CREATED));
    }

    @Operation(summary = SUMMARY_GET_A_PAGINATED_LIST_OF_CATEGORIES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = LIST_OF_CATEGORIES, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageStock.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST, description = INVALID_REQUEST, content = @Content)
    })
    @GetMapping
    ResponseEntity<PageStock<CategoryResponse>> getCategoriesByName(
            @RequestParam(name = SORT_DIRECTION, defaultValue = ASC) String sortDirection,
            @RequestParam(name = PAGE, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = SIZE, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(categoryHandler.getCategoriesByName(page, size, sortDirection));
    }

    @Operation(summary = SUMMARY_GET_A_CATEGORY_BY_NUMBER)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_SUCCESS, description = CATEGORY_FOUND, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = RESPONSE_CODE_NOT_FOUND, description = NOT_FOUND, content = @Content)
    })
    @GetMapping("/{number}")
    ResponseEntity<CategoryResponse> getCategory(@PathVariable(name = NUMBER) Long categoryNumber) {
        return ResponseEntity.ok(categoryHandler.getCategory(categoryNumber));
    }


    @Operation(summary = SUMMARY_ADD_A_NEW_CATEGORY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = RESPONSE_CODE_CREATED, description = CATEGORY_CREATED, content = @Content),
            @ApiResponse(responseCode = RESPONSE_CODE_CONFLICT, description = CATEGORY_ALREADY_EXISTS, content = @Content)
    })
    @PostMapping("/validate-name")
    ResponseEntity<Boolean> isCategoryNameAvailable(@RequestBody String categoryName) {
        return ResponseEntity.ok( categoryHandler.isCategoryNameAvailable(categoryName));
    }
}
