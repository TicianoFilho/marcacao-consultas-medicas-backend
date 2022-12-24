package br.com.cubo.marcacaoconsultamedica.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetailsDto {

	private LocalDateTime timestamp;
	private String message;
	private String details;
	
}
