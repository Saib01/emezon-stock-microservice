package com.emazon.stock.infraestructura.exceptionhandler;

import com.emazon.stock.dominio.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
        private static final String MESSAGE = "Message";

        @ExceptionHandler(CategoryDescriptionTooLongException.class)
        public ResponseEntity<Map<String, String>> handleCategoryDescriptionTooLongException(
                CategoryDescriptionTooLongException categoryDescriptionTooLongException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_DESCRIPTION_TOO_LONG.getMessage()));
        }

        @ExceptionHandler(CategoryNameTooLongException.class)
        public ResponseEntity<Map<String, String>> handleCategoryNameTooLongException(
                CategoryNameTooLongException categoryNameTooLongException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_NAME_TOO_LONG.getMessage()));
        }

        @ExceptionHandler(CategoryAlreadyExistException.class)
        public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(
                CategoryAlreadyExistException categoryAlreadyExistException) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_ALREADY_EXISTS.getMessage()));
        }

        @ExceptionHandler(CategoryDescriptionRequiredException.class)
        public ResponseEntity<Map<String, String>> handleCategoryDescriptionRequiredException(
                CategoryDescriptionRequiredException categoryDescriptionRequiredException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_DESCRIPTION_REQUIRED.getMessage()));
        }

        @ExceptionHandler(CategoryNameRequiredException.class)
        public ResponseEntity<Map<String, String>> handleCategoryNameRequiredException(
                CategoryNameRequiredException categoryNameRequiredException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_NAME_REQUIRED.getMessage()));
        }

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
                CategoryNotFoundException categoryNotFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_NOT_FOUND.getMessage()));
        }

        @ExceptionHandler(CategoryPageSortDirectionIsInvalidException.class)
        public ResponseEntity<Map<String, String>> handleCategoryPageSortDirectionInvalid(
                        CategoryPageSortDirectionIsInvalidException categoryPageSortDirectionIsInvalidException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_PAGE_SORT_DIRECTION_IS_INVALID.getMessage()));
        }

        @ExceptionHandler(CategoryPageSizeIsInvalidException.class)
        public ResponseEntity<Map<String, String>> handleCategoryPageSizeInvalid(
                CategoryPageSizeIsInvalidException categoryPageSizeIsInvalidException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_PAGE_SIZE_NUMBER_IS_INVALID.getMessage()));
        }

        @ExceptionHandler(CategoryPageNumberIsInvalidException.class)
        public ResponseEntity<Map<String, String>> handleCategoryPageNumberInvalid(
                CategoryPageNumberIsInvalidException categoryNumberIsInvalidException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap(MESSAGE,
                                ExceptionResponse.CATEGORY_PAGE_NUMBER_IS_INVALID.getMessage()));
        }
}
