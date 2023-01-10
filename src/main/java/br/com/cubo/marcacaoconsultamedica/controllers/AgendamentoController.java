package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import br.com.cubo.marcacaoconsultamedica.dtos.AgendamentoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.AgendamentoUpdateDto;
import br.com.cubo.marcacaoconsultamedica.dtos.EspecialidadeDto;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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
	@Operation(
			tags = {"Agendamento"},
			operationId = "getAllAgendamentos",
			summary = "Busca todas os agendamentos.",
			description = "Busca todos os agendamentos existentes no banco de dados. Traz os dados com paginação.",
			security = @SecurityRequirement(name = "BearerJWT"),
					responses = {
							@ApiResponse(responseCode = "200",
								content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE), 
										description = "A busca foi realizada com sucesso."),
							@ApiResponse(responseCode = "401",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE), 
									description = "Acesso não autorizado."),
							@ApiResponse(responseCode = "404",
								content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE), 
										description = "Nenhum registro encontrado."),
					})
	public ResponseEntity<Page<Agendamento>> getAllAgendamentos(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {	
		Page<Agendamento> agendamentoPage = agendamentoService.findAll(pageable);
		if (agendamentoPage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(agendamentoPage);
	}
	
	@GetMapping("/{id}") 
	@Operation(
			tags = {"Agendamento"},
			operationId = "getAgendamentoById",
			summary = "Busca o agendamento pelo ID.",
			description = "Busca o agendamento existente no banco de dados pelo ID.",
			security = @SecurityRequirement(name = "BearerJWT"),
					responses = {
							@ApiResponse(responseCode = "200",
								content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE), 
										description = "A busca foi realizada com sucesso."),
							@ApiResponse(responseCode = "401",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE), 
									description = "Acesso não autorizado."),
							@ApiResponse(responseCode = "404",
								content = @Content(
										mediaType = MediaType.APPLICATION_JSON_VALUE), 
										description = "Nenhum registro encontrado."),
					})
	public ResponseEntity<Object> getAgendamentoById(@PathVariable(name = "id") UUID id) {		
		Agendamento agendamento = agendamentoService.findOneById(id).orElseThrow(
				() -> new ResourceAccessException(AppMessages.AGENDAMENTO_NOT_FOUND));
		return ResponseEntity.ok(agendamento);
	}
	
	@PostMapping
	@Operation(
			tags = {"Agendamento"},
			operationId = "saveAgendamento",
			summary = "Cria um agendamento.",
			description = "Cria um agendamento no banco de dados.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			security = @SecurityRequirement(name = "BearerJWT"),
					responses = {
							@ApiResponse(responseCode = "201",
								content = @Content(
										schema = @Schema(implementation = AgendamentoDto.class),
										mediaType = MediaType.APPLICATION_JSON_VALUE), 
										description = "O registro foi criado com sucesso."),
							@ApiResponse(responseCode = "400",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE), 
									description = "Algum campo requerido pode não ter sido passado no corpo da requisição, ou mal formatado."),
							@ApiResponse(responseCode = "401",
							content = @Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE), 
									description = "Acesso não autorizado."),
					})
	public ResponseEntity<Response<AgendamentoDto>> saveAgendamento(@RequestBody @Valid AgendamentoDto agendamentoDto,
			BindingResult result) {
		
		Response<AgendamentoDto> response = new Response<>();		
		if (VerificaDtoComErroValidacao.existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Paciente paciente = pacienteService.findOneById(agendamentoDto.getPacienteId()).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.PACIENTE_NOT_FOUND));
		
		if (!paciente.isAtivo()) {
			response.getErrors().add(AppMessages.AGENDAMENTO_NEGADO_PACIENTE_INATIVO);
			return ResponseEntity.badRequest().body(response);
		}
		
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
	
	@PutMapping("/{id}")
	@Operation(
			tags = {"Agendamento"},
			operationId = "updateAgendamento",
			summary = "Atualiza o agendamento.",
			description = "Atualiza o agendamento banco de dados.",
			security = @SecurityRequirement(name = "BearerJWT"),
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			responses = {
					@ApiResponse(responseCode = "200",
						content = @Content(
								schema = @Schema(implementation = AgendamentoUpdateDto.class),
								mediaType = MediaType.APPLICATION_JSON_VALUE), 
								description = "Registro alterado com sucesso."),
					@ApiResponse(responseCode = "400",
						content = @Content(
								schema = @Schema(implementation = Response.class), 
								mediaType = MediaType.APPLICATION_JSON_VALUE),
								description = "Algum campo requerido pode não ter sido passado no corpo da requisição, ou mal formatado."),
					@ApiResponse(responseCode = "401",
						content = @Content(
								mediaType = MediaType.APPLICATION_JSON_VALUE), 
								description = "Acesso não autorizado."),
					@ApiResponse(responseCode = "404",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE), 
							description = "O registro com o ID informado não foi encontrado."),
			})
	public ResponseEntity<Response<AgendamentoUpdateDto>> updateAgendamento(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid AgendamentoUpdateDto agendamentoUpdateDto, BindingResult result) {
		
		Response<AgendamentoUpdateDto> response = new Response<>();	
		
		if (VerificaDtoComErroValidacao.existeErroDeValidacao(response, result)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Agendamento agendamento = agendamentoService.findOneById(id).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.AGENDAMENTO_NOT_FOUND));
		
		updateAgendamentoFields(agendamentoUpdateDto, agendamento);
		
		agendamentoService.save(agendamento);
		response.setData(agendamentoUpdateDto);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			tags = {"Agendamento"},
			operationId = "deleteAgendamento",
			summary = "Exclui o agendamento.",
			description = "Exclui o agendamento no banco de dados.",
			security = @SecurityRequirement(name = "BearerJWT"),
			responses = {
					@ApiResponse(responseCode = "200",
						content = @Content(
								schema = @Schema(implementation = String.class),
								mediaType = MediaType.APPLICATION_JSON_VALUE), 
								description = "A busca das Unidades foi realizada com sucesso."),
					@ApiResponse(responseCode = "401",
						content = @Content(
								mediaType = MediaType.APPLICATION_JSON_VALUE), 
								description = "Acesso não autorizado."),
					@ApiResponse(responseCode = "404",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE), 
							description = "O registro com o ID informado não foi encontrado."),
			})
	public ResponseEntity<String> deleteAgendamento(@PathVariable(name = "id") UUID id) {
		
		Agendamento agendamento = agendamentoService.findOneById(id).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.AGENDAMENTO_NOT_FOUND));
		
		agendamentoService.delete(agendamento);
		
		return ResponseEntity.ok(String.format("O agendamento de id=%s foi excluído com sucesso.", id));
	}
	
	private void updateAgendamentoFields(AgendamentoUpdateDto agendamentoUpdateDto, Agendamento agendamento) {
		if (agendamentoUpdateDto.getData() != null) {
			agendamento.setData(agendamentoUpdateDto.getData());
		}
		if (agendamentoUpdateDto.getHora() != null) {
			agendamento.setHora(agendamentoUpdateDto.getHora());
		}
	}
}
