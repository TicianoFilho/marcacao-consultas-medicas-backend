package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import br.com.cubo.marcacaoconsultamedica.dtos.EspecialidadeDto;
import br.com.cubo.marcacaoconsultamedica.dtos.TipoPlanoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UnidadeUpdateDto;
import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

	private final EspecialidadeService especialidadeService;

	public EspecialidadeController(EspecialidadeService especialidadeService) {
		this.especialidadeService = especialidadeService;
	}
	
	@GetMapping
	@Operation(
			tags = {"Especialidade"},
			operationId = "getAllEspecialidade",
			summary = "Busca todas as especialidades.",
			description = "Busca todas as especialidades existentes no banco de dados. Traz os dados com paginação.",
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
	public ResponseEntity<Page<Especialidade>> getAllEspecialidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<Especialidade> especialidadePage = especialidadeService.findAll(pageable);
		if (especialidadePage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(especialidadePage);		
	}
	
	@GetMapping("/{id}") 
	@Operation(
			tags = {"Especialidade"},
			operationId = "getEspecialidadeById",
			summary = "Busca a especialidade pelo ID",
			description = "Busca a especialidade existente no banco de dados pelo ID.",
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
	public ResponseEntity<Object> getEspecialidadeById(@PathVariable(name = "id") UUID id) {
		
		Optional<Especialidade> especialidadeOptional = especialidadeService.findOneById(id);
		if (!especialidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.ESPECIALIDADE_NOT_FOUND);
		}
		return ResponseEntity.ok(especialidadeOptional.get());
	}
	
	@PostMapping
	@Operation(
			tags = {"Especialidade"},
			operationId = "saveEspecialidade",
			summary = "Cria uma especialidade.",
			description = "Cria uma especialidade no banco de dados.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			security = @SecurityRequirement(name = "BearerJWT"),
					responses = {
							@ApiResponse(responseCode = "201",
								content = @Content(
										schema = @Schema(implementation = EspecialidadeDto.class),
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
	public ResponseEntity<Response<EspecialidadeDto>> saveEspecialidade(@Valid @RequestBody EspecialidadeDto especialidadeDto,
			BindingResult result) {
		
		Response<EspecialidadeDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Especialidade especialidade = new Especialidade();
		BeanUtils.copyProperties(especialidadeDto, especialidade);
		Especialidade newEspecialidade = especialidadeService.save(especialidade);
		BeanUtils.copyProperties(newEspecialidade, especialidadeDto);
		response.setData(especialidadeDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	@Operation(
			tags = {"Especialidade"},
			operationId = "updadeEspecialidade",
			summary = "Atualiza a especialidade.",
			description = "Atualiza a especialidade banco de dados.",
			security = @SecurityRequirement(name = "BearerJWT"),
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			responses = {
					@ApiResponse(responseCode = "200",
						content = @Content(
								schema = @Schema(implementation = EspecialidadeDto.class),
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
	public ResponseEntity<Response<EspecialidadeDto>> updateEspecialidade(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EspecialidadeDto especialidadeDto, BindingResult result) {
		
		Response<EspecialidadeDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Especialidade> especialidadeOptional = especialidadeService.findOneById(id);
		if (!especialidadeOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Especialidade especialidade = new Especialidade();
		BeanUtils.copyProperties(especialidadeDto, especialidade);
		especialidade.setId(especialidadeOptional.get().getId());
		
		Especialidade updatedEspecialidade = especialidadeService.save(especialidade);
		BeanUtils.copyProperties(updatedEspecialidade, especialidadeDto);
		response.setData(especialidadeDto);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			tags = {"Especialidade"},
			operationId = "deleteEspecialidade",
			summary = "Exclui a especialidade.",
			description = "Exclui a especialidade no banco de dados.",
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
	public ResponseEntity<String> deleteEspecialidade(@PathVariable(name = "id") UUID id) {
		
		Optional<Especialidade> especialidadeOptional = especialidadeService.findOneById(id);
		if (!especialidadeOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		especialidadeService.delete(especialidadeOptional.get());
		
		return ResponseEntity.ok(String.format("Especialidade de id=%s excluído com sucesso.", id));
	}
	
	private boolean existeErroDeValidacao(Response<EspecialidadeDto> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
}
