package br.com.cubo.marcacaoconsultamedica.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.entities.Paciente;
import br.com.cubo.marcacaoconsultamedica.services.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

	private final PacienteService pacienteService;

	public PacienteController(PacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Paciente>> getAllPacientes(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(pacienteService.findAll(pageable));		
	}
	
}
