package dev.mrkevr.quizgenerator.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.mrkevr.quizgenerator.dto.HttpErrorResponse;
import dev.mrkevr.quizgenerator.exception.InvalidFileFormatException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(InvalidFileFormatException.class)
	public ResponseEntity<?> handleInvalidFileFormatException(InvalidFileFormatException ex) {
		
		HttpErrorResponse response = new HttpErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception ex) {
		
		HttpErrorResponse response = new HttpErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
}
