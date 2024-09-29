package com.emazon.stock.infraestructura.exceptionhandler;

import com.emazon.stock.dominio.exeption.brand.*;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String MESSAGE = "Response";

    private ResponseEntity<Map<String, String>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(MESSAGE, message));
    }

    @ExceptionHandler({
            CategoryDescriptionTooLongException.class,
            CategoryNameTooLongException.class,
            CategoryDescriptionRequiredException.class,
            CategoryNameRequiredException.class,
            CategoryPageSortDirectionIsInvalidException.class,
            CategoryPageSizeIsInvalidException.class,
            CategoryPageNumberIsInvalidException.class,
            CategoryDuplicateException.class,
            CategoryListSizeException.class,
            BrandDescriptionTooLongException.class,
            BrandNameTooLongException.class,
            BrandDescriptionRequiredException.class,
            BrandNameRequiredException.class,
            BrandPageSortDirectionIsInvalidException.class,
            BrandPageSizeIsInvalidException.class,
            BrandPageNumberIsInvalidException.class,
            ProductDescriptionRequiredException.class,
            ProductNameRequiredException.class,
            ProductAmountInvalidException.class,
            ProductPriceInvalidException.class,
            ProductPageSortDirectionIsInvalidException.class,
            ProductPageSizeIsInvalidException.class,
            ProductPageNumberIsInvalidException.class,
            ProductPageSortByIsInvalidException.class,
            ProductListSizeException.class,
            InvalidSupplyException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequestExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({
            CategoryAlreadyExistException.class,
            BrandAlreadyExistException.class,
            ProductAlreadyExistException.class
    })
    public ResponseEntity<Map<String, String>> handleConflictExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({
            CategoryNotFoundException.class,
            BrandNotFoundException.class,
            ProductNotFoundException.class,
            ProductFilterNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
