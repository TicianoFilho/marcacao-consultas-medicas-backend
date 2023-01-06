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
import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
	public ResponseEntity<Object> getEspecialidadeById(@PathVariable(name = "id") UUID id) {
		
		Optional<Especialidade> especialidadeOptional = especialidadeService.findOneById(id);
		if (!especialidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.ESPECIALIDADE_NOT_FOUND);
		}
		return ResponseEntity.ok(especialidadeOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<Response<Especialidade>> saveEspecialidade(@Valid @RequestBody EspecialidadeDto especialidadeDto,
			BindingResult result) {
		
		Response<Especialidade> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Especialidade especialidade = new Especialidade();
		BeanUtils.copyProperties(especialidadeDto, especialidade);
		Especialidade newEspecialidade = especialidadeService.save(especialidade);
		response.setData(newEspecialidade);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Especialidade>> updateEspecialidade(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EspecialidadeDto especialidadeDto, BindingResult result) {
		
		Response<Especialidade> response = new Response<>();
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
		response.setData(updatedEspecialidade);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEspecialidade(@PathVariable(name = "id") UUID id) {
		
		Optional<Especialidade> especialidadeOptional = especialidadeService.findOneById(id);
		if (!especialidadeOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		especialidadeService.delete(especialidadeOptional.get());
		
		return ResponseEntity.ok(String.format("Especialidade de id=%s excluído com sucesso.", id));
	}
	
	private boolean existeErroDeValidacao(Response<Especialidade> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
}
