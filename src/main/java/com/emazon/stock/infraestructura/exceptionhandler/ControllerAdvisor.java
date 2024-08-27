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
        public ResponseEntity<Map<String, String>> handleMaxCharDescriptionException(
                        CategoryDescriptionTooLongException categoryDescriptionTooLongException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_DESCRIPTION_TOO_LONG.getMessage()));
        }

        @ExceptionHandler(CategoryNameTooLongException.class)
        public ResponseEntity<Map<String, String>> handleMaxCharNameException(
                        CategoryNameTooLongException categoryNameTooLongException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_NAME_TOO_LONG.getMessage()));
        }

        @ExceptionHandler(CategoryAlreadyExistException.class)
        public ResponseEntity<Map<String, String>> handleErrorNameRepeated(
                        CategoryAlreadyExistException categoryAlreadyExistException) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_ALREADY_EXISTS.getMessage()));
        }

        @ExceptionHandler(CategoryDescriptionRequiredException.class)
        public ResponseEntity<Map<String, String>> handleErrorNoDescription(
                        CategoryDescriptionRequiredException categoryDescriptionRequiredException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_DESCRIPTION_REQUIRED.getMessage()));
        }

        @ExceptionHandler(CategoryNameRequiredException.class)
        public ResponseEntity<Map<String, String>> handleErrorNoDescription(
                        CategoryNameRequiredException categoryNameRequiredException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_NAME_REQUIRED.getMessage()));
        }

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleErrorNoFound(
                        CategoryNotFoundException categoryNotFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.CATEGORY_NOT_FOUND.getMessage()));
        }

        @ExceptionHandler(SortDirectionIsInvalidException.class)
        public ResponseEntity<Map<String, String>> handleErrorInvalidSortDirection(
                        SortDirectionIsInvalidException categorySortDirectionException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap(MESSAGE,
                                                ExceptionResponse.SORT_DIRECTION_IS_INVALID.getMessage()));
        }

}
