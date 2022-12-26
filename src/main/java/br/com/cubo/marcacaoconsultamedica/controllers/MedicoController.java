package br.com.cubo.marcacaoconsultamedica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.services.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

	private final MedicoService medicoService;

	public MedicoController(MedicoService medicoService) {
		this.medicoService = medicoService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Medico>> getAllMedicos(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(medicoService.findAll(pageable));		
	}
}
