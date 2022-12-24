package br.com.cubo.marcacaoconsultamedica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

	private final EspecialidadeService especialidadeService;

	public EspecialidadeController(EspecialidadeService especialidadeService) {
		this.especialidadeService = especialidadeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Especialidade>> getAllEspecialidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(especialidadeService.findAll(pageable));		
	}
	
}
