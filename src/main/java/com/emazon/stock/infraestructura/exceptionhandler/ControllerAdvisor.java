package com.emazon.stock.infraestructura.exceptionhandler;

import com.emazon.stock.dominio.exeption.brand.BrandNotFoundException;
import com.emazon.stock.dominio.exeption.brand.BrandAlreadyExistException;
import com.emazon.stock.dominio.exeption.brand.BrandNameTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandNameRequiredException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionTooLongException;
import com.emazon.stock.dominio.exeption.brand.BrandDescriptionRequiredException;
import com.emazon.stock.dominio.exeption.category.*;
import com.emazon.stock.dominio.exeption.brand.BrandPageSortDirectionIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageSizeIsInvalidException;
import com.emazon.stock.dominio.exeption.brand.BrandPageNumberIsInvalidException;
import com.emazon.stock.dominio.exeption.product.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.emazon.stock.infraestructura.exceptionhandler.ExceptionResponse.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
        private static final String MESSAGE = "Message";
        private static final Map<Class<? extends Exception>, String> exceptionMap = new HashMap<>();

        static {

                exceptionMap.put(CategoryDescriptionTooLongException.class,CATEGORY_DESCRIPTION_TOO_LONG.getMessage());
                exceptionMap.put(CategoryNameTooLongException.class,CATEGORY_NAME_TOO_LONG.getMessage());
                exceptionMap.put(CategoryAlreadyExistException.class,CATEGORY_ALREADY_EXISTS.getMessage());
                exceptionMap.put(CategoryDescriptionRequiredException.class,CATEGORY_DESCRIPTION_REQUIRED.getMessage());
                exceptionMap.put(CategoryNameRequiredException.class,CATEGORY_NAME_REQUIRED.getMessage());
                exceptionMap.put(CategoryNotFoundException.class,CATEGORY_NOT_FOUND.getMessage());
                exceptionMap.put(CategoryPageSortDirectionIsInvalidException.class,CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID.getMessage());
                exceptionMap.put(CategoryPageSizeIsInvalidException.class,CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID.getMessage());
                exceptionMap.put(CategoryPageNumberIsInvalidException.class,CATEGORY_PAGE_NUMBER_IS_INVALID.getMessage());
                exceptionMap.put(CategoryDuplicateException.class,CATEGORY_DUPLICATE.getMessage());
                exceptionMap.put(CategoryListSizeException.class,CATEGORY_LIST_SIZE.getMessage());

                exceptionMap.put(BrandDescriptionTooLongException.class,BRAND_DESCRIPTION_TOO_LONG.getMessage());
                exceptionMap.put(BrandNameTooLongException.class,BRAND_NAME_TOO_LONG.getMessage());
                exceptionMap.put(BrandAlreadyExistException.class,BRAND_ALREADY_EXISTS.getMessage());
                exceptionMap.put(BrandDescriptionRequiredException.class,BRAND_DESCRIPTION_REQUIRED.getMessage());
                exceptionMap.put(BrandNameRequiredException.class,BRAND_NAME_REQUIRED.getMessage());
                exceptionMap.put(BrandNotFoundException.class,BRAND_NOT_FOUND.getMessage());
                exceptionMap.put(BrandPageSortDirectionIsInvalidException.class,BRAND_PAGE_SORT_DIRECTION_IS_INVALID.getMessage());
                exceptionMap.put(BrandPageSizeIsInvalidException.class,BRAND_PAGE_SIZE_NUMBER_IS_INVALID.getMessage());
                exceptionMap.put(BrandPageNumberIsInvalidException.class,BRAND_PAGE_NUMBER_IS_INVALID.getMessage());

                exceptionMap.put(ProductAlreadyExistException.class,PRODUCT_ALREADY_EXISTS.getMessage());
                exceptionMap.put(ProductDescriptionRequiredException.class,PRODUCT_DESCRIPTION_REQUIRED.getMessage());
                exceptionMap.put(ProductNameRequiredException.class,PRODUCT_NAME_REQUIRED.getMessage());
                exceptionMap.put(ProductNotFoundException.class,PRODUCT_NOT_FOUND.getMessage());
                exceptionMap.put(ProductAmountInvalidException.class,PRODUCT_AMOUNT_GREATER_THAN_ZERO.getMessage());
                exceptionMap.put(ProductPriceInvalidException.class,PRODUCT_PRICE_GREATER_THAN_ZERO.getMessage());
                exceptionMap.put(ProductPageSortDirectionIsInvalidException.class,PRODUCT_PAGE_SORT_DIRECTION_IS_INVALID.getMessage());
                exceptionMap.put(ProductPageSizeIsInvalidException.class,PRODUCT_PAGE_SIZE_NUMBER_IS_INVALID.getMessage());
                exceptionMap.put(ProductPageNumberIsInvalidException.class,PRODUCT_PAGE_NUMBER_IS_INVALID.getMessage());
                exceptionMap.put(ProductPageSortByIsInvalidException.class,PRODUCT_PAGE_SORT_BY_IS_INVALID.getMessage());
                exceptionMap.put(InvalidSupplyException.class,SUPPLY_IS_INVALID.getMessage());

        }

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
                InvalidSupplyException.class
        })
        public ResponseEntity<Map<String, String>> handleBadRequestExceptions(RuntimeException ex) {
                String message = exceptionMap.get(ex.getClass());
                return buildResponse(HttpStatus.BAD_REQUEST, message);
        }
        @ExceptionHandler({
                CategoryAlreadyExistException.class,
                BrandAlreadyExistException.class,
                ProductAlreadyExistException.class
        })
        public ResponseEntity<Map<String, String>> handleConflictExceptions(RuntimeException ex) {
                String message = exceptionMap.get(ex.getClass());
                return buildResponse(HttpStatus.CONFLICT, message);
        }
        @ExceptionHandler({
                CategoryNotFoundException.class,
                BrandNotFoundException.class,
                ProductNotFoundException.class
        })
        public ResponseEntity<Map<String, String>> handleNotFoundExceptions(RuntimeException ex) {
                String message = exceptionMap.get(ex.getClass());
                return buildResponse(HttpStatus.NOT_FOUND, message);
        }
}
