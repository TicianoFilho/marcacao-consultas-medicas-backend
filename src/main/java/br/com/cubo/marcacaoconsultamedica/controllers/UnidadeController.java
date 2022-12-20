package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;

@RestController
public class UnidadeController {

	private final UnidadeService unidadeService;

	public UnidadeController(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}
	
	@GetMapping
	public ResponseEntity<Object> findAll() {
		List<Unidade> unidades = unidadeService.findAll();
		return null;
	}
}
