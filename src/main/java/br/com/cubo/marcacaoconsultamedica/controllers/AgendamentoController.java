package br.com.cubo.marcacaoconsultamedica.controllers;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import br.com.cubo.marcacaoconsultamedica.dtos.AgendamentoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.PacienteTipoPlanoUpdateDto;
import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;
import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.entities.Paciente;
import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.enums.SituacaoAgendamento;
import br.com.cubo.marcacaoconsultamedica.exceptions.ResourceNotFoundException;
import br.com.cubo.marcacaoconsultamedica.services.AgendamentoService;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;
import br.com.cubo.marcacaoconsultamedica.services.MedicoService;
import br.com.cubo.marcacaoconsultamedica.services.PacienteService;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;
import br.com.cubo.marcacaoconsultamedica.utils.VerificaDtoComErroValidacao;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

	private final AgendamentoService agendamentoService;
	private final PacienteService pacienteService;
	private final MedicoService medicoService;
	private final UnidadeService unidadeService;
	private final EspecialidadeService especialidadeService;

	public AgendamentoController(AgendamentoService agendamentoService, PacienteService pacienteService,
			MedicoService medicoService, UnidadeService unidadeService, EspecialidadeService especialidadeService) {
		this.agendamentoService = agendamentoService;
		this.pacienteService = pacienteService;
		this.medicoService = medicoService;
		this.unidadeService = unidadeService;
		this.especialidadeService = especialidadeService;
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
	
	@PostMapping
	public ResponseEntity<Response<AgendamentoDto>> saveAgendamento(@RequestBody @Valid AgendamentoDto agendamentoDto,
			BindingResult result) {
		
		Response<AgendamentoDto> response = new Response<>();
		
		if (VerificaDtoComErroValidacao.existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Paciente paciente = pacienteService.findOneById(agendamentoDto.getPacienteId()).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.PACIENTE_NOT_FOUND));
		
		Medico medico = medicoService.findOneById(agendamentoDto.getMedicoId()).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.MEDICO_NOT_FOUND));
		
		Unidade unidade = unidadeService.findOneById(agendamentoDto.getUnidadeId()).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.UNIDADE_NOT_FOUND));
		
		Especialidade especialidade = especialidadeService.findOneById(agendamentoDto.getEspecialidadeId()).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.ESPECIALIDADE_NOT_FOUND));
		
		Agendamento agendamento = new Agendamento();
		agendamento.setData(agendamentoDto.getData());
		agendamento.setHora(agendamentoDto.getHora());
		agendamento.setSituacao(SituacaoAgendamento.EM_ABERTO);
		agendamento.setPaciente(paciente);
		agendamento.setMedico(medico);
		agendamento.setUnidade(unidade);
		agendamento.setEspecialidade(especialidade);

		Agendamento newAgendamento = agendamentoService.save(agendamento);	
		
		agendamentoDto.setData(newAgendamento.getData());
		agendamentoDto.setHora(newAgendamento.getHora());
		agendamentoDto.setPacienteId(newAgendamento.getPaciente().getId());
		agendamentoDto.setMedicoId(newAgendamento.getMedico().getId());
		agendamentoDto.setUnidadeId(newAgendamento.getUnidade().getId());
		agendamentoDto.setEspecialidadeId(newAgendamento.getEspecialidade().getId());
		agendamentoDto.setId(newAgendamento.getId());
		
		response.setData(agendamentoDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
