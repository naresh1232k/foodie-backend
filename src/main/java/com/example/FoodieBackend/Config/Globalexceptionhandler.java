package com.example.FoodieBackend.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
import java.util.HashMap;
import java.util.Map;
 
@RestControllerAdvice
public class Globalexceptionhandler {
 
	@RestControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("message", ex.getMessage()));
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();

	        ex.getBindingResult().getAllErrors().forEach(err -> {
	            String field = err instanceof FieldError
	                    ? ((FieldError) err).getField()
	                    : err.getObjectName();

	            String msg = err.getDefaultMessage();
	            errors.put(field, msg);
	        });

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	    }

	    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	    public ResponseEntity<Map<String, String>> handleAccessDenied(
	            org.springframework.security.access.AccessDeniedException ex) {

	        return ResponseEntity
	                .status(HttpStatus.FORBIDDEN)
	                .body(Map.of("message", "Access denied"));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("message", "Something went wrong"));
	    }
	}
}