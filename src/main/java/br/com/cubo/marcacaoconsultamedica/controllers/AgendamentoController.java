package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;
import br.com.cubo.marcacaoconsultamedica.services.AgendamentoService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

	private final AgendamentoService agendamentoService;

	public AgendamentoController(AgendamentoService agendamentoService) {
		this.agendamentoService = agendamentoService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Agendamento>> getAllAgendamentos(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(agendamentoService.findAll(pageable));		
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Object> getAgendamentoById(@PathVariable(name = "id") UUID id) {		
		Agendamento agendamento = agendamentoService.findOneById(id).orElseThrow(
				() -> new ResourceAccessException(AppMessages.AGENDAMENTO_NOT_FOUND));
		return ResponseEntity.ok(agendamento);
	}
	
}
