package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

	private final UnidadeService unidadeService;

	public UnidadeController(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}
	

	@GetMapping
	public ResponseEntity<Page<Unidade>> getAllUnidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(unidadeService.findAll(pageable));		
	}
}
