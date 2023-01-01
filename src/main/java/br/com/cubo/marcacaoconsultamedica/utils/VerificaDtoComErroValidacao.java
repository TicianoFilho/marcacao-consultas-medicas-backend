package br.com.cubo.marcacaoconsultamedica.utils;

import org.springframework.validation.BindingResult;

public class VerificaDtoComErroValidacao {

	public static boolean existeErroDeValidacao(Response<? extends Object> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
}
