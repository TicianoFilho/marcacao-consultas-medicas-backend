package br.com.cubo.marcacaoconsultamedica.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cubo.marcacaoconsultamedica.dtos.ErrorDetailsDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception exception, WebRequest webRequest) {
		ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(
				LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetailsDto);
	}
}
