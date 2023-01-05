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

import br.com.cubo.marcacaoconsultamedica.dtos.TipoPlanoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UnidadeDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UnidadeUpdateDto;
import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.services.TipoPlanoService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/tipo-plano")
public class TipoPlanoController {

	private final TipoPlanoService tipoPlanoService;

	public TipoPlanoController(TipoPlanoService tipoPlanoService) {
		this.tipoPlanoService = tipoPlanoService;
	}
	
	@GetMapping
	@Operation(
			tags = {"Tipo de Plano"},
			operationId = "getAllTipoPlano",
			summary = "Busca todos os tipos de planos existentes",
			description = "Busca todos os tipos de planos existentes no banco de dados. Traz os dados com paginação.",
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
	public ResponseEntity<Page<TipoPlano>> getAllTipoPlano(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		Page<TipoPlano> tipoPlanoPage = tipoPlanoService.findAll(pageable);
		if (tipoPlanoPage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(tipoPlanoPage);		
	}
	
	@GetMapping("/{id}") 
	@Operation(
			tags = {"Tipo de Plano"},
			operationId = "getTipoPlanoById",
			summary = "Busca o tipo de plano pelo ID",
			description = "Busca o tipo de planos existente no banco de dados pelo ID.",
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
	public ResponseEntity<Object> getTipoPlanoById(@PathVariable(name = "id") UUID id) {
		
		Optional<TipoPlano> tipoPlanoOptional = tipoPlanoService.findOneById(id);
		if (!tipoPlanoOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.TIPO_PLANO_NOT_FOUND);
		}
		return ResponseEntity.ok(tipoPlanoOptional.get());
	}
	
	@PostMapping
	@Operation(
			tags = {"Tipo de Plano"},
			operationId = "saveTipoPlano",
			summary = "Cria um novo tipo de plano.",
			description = "Cria um novo tipo de plano no banco de dados.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			security = @SecurityRequirement(name = "BearerJWT"),
					responses = {
							@ApiResponse(responseCode = "201",
								content = @Content(
										schema = @Schema(implementation = TipoPlanoDto.class),
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
	public ResponseEntity<Response<TipoPlanoDto>> saveTipoPlano(@Valid @RequestBody TipoPlanoDto tipoPlanoDto,
			BindingResult result) {
		
		Response<TipoPlanoDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		TipoPlano tipoPlano = new TipoPlano();
		BeanUtils.copyProperties(tipoPlanoDto, tipoPlano);
		TipoPlano newTipoPlano = tipoPlanoService.save(tipoPlano);
		BeanUtils.copyProperties(newTipoPlano, tipoPlanoDto);
		response.setData(tipoPlanoDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	@Operation(
			tags = {"Tipo de Plano"},
			operationId = "updateTipoPlano",
			summary = "Atualiza o tipo de plano.",
			description = "Atualiza o tipo de plano no banco de dados.",
			security = @SecurityRequirement(name = "BearerJWT"),
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			responses = {
					@ApiResponse(responseCode = "200",
						content = @Content(
								schema = @Schema(implementation = UnidadeUpdateDto.class),
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
	public ResponseEntity<Response<TipoPlanoDto>> updateTipoPlano(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid TipoPlanoDto tipoPlanoDto, BindingResult result) {
		
		Response<TipoPlanoDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<TipoPlano> tipoPlanoOptional = tipoPlanoService.findOneById(id);
		if (!tipoPlanoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		TipoPlano tipoPlano = new TipoPlano();
		BeanUtils.copyProperties(tipoPlanoDto, tipoPlano);
		tipoPlano.setId(tipoPlanoOptional.get().getId());
		
		TipoPlano updatedTipoPlano = tipoPlanoService.save(tipoPlano);
		BeanUtils.copyProperties(updatedTipoPlano, tipoPlanoDto);
		response.setData(tipoPlanoDto);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			tags = {"Tipo de Plano"},
			operationId = "deleteTipoPlano",
			summary = "Exclui o tipo de plano.",
			description = "Exclui o tipo de plano no banco de dados.",
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
	public ResponseEntity<String> deleteTipoPlano(@PathVariable(name = "id") UUID id) {
		
		Optional<TipoPlano> tipoPlanoOptional = tipoPlanoService.findOneById(id);
		if (!tipoPlanoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		tipoPlanoService.delete(tipoPlanoOptional.get());
		
		return ResponseEntity.ok(String.format("Tipo de Plano de id=%s excluído com sucesso.", id));
	}
	
	private boolean existeErroDeValidacao(Response<TipoPlanoDto> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
}
