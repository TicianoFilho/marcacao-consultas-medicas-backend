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

import br.com.cubo.marcacaoconsultamedica.dtos.EnderecoUpdateDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UnidadeDto;
import br.com.cubo.marcacaoconsultamedica.entities.Endereco;
import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.services.EnderecoService;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

	private final UnidadeService unidadeService;
	private final EnderecoService enderecoService;

	public UnidadeController(UnidadeService unidadeService, EnderecoService enderecoService) {
		this.unidadeService = unidadeService;
		this.enderecoService = enderecoService;
	}
	
	@GetMapping
	@Operation(
			tags = {"Unidade"},
			operationId = "getAllUnidades",
			summary = "Busca todas as Unidades existentes",
			description = "Busca todas as Unidades existentes no banco de dados. Traz os dados com paginação.")
	public ResponseEntity<Page<Unidade>> getAllUnidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		
		Page<Unidade> UnidadesPage = unidadeService.findAll(pageable);
		if (UnidadesPage.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().body(unidadeService.findAll(pageable));		
	}
	
	@GetMapping("/{id}") 
	@Operation(
			tags = {"Unidade"})
	public ResponseEntity<Object> getUnidadeById(@PathVariable(name = "id") UUID id) {
		
		Optional<Unidade> unidadeOptional = unidadeService.findOneById(id);
		if (!unidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.UNIDADE_NOT_FOUND);
		}
		return ResponseEntity.ok(unidadeOptional.get());
	}
	
	@PostMapping
	@Operation(
			tags = {"Unidade"},
			operationId = "saveUnidade",
			summary = "Cria uma nova Unidade",
			description = "Cria um novo registro de Unidade no banco de dados.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto que será enviado no corpo da requisição."),
			responses = {
					@ApiResponse(responseCode = "200",
						content = @Content(schema = @Schema(implementation = UnidadeDto.class),
						mediaType = MediaType.APPLICATION_JSON_VALUE), 
						description = "O Registro foi salvo com sucesso."),
					@ApiResponse(responseCode = "400",
						content = @Content(schema = @Schema(implementation = Response.class),
						mediaType = MediaType.APPLICATION_JSON_VALUE),
						description = "Algum campo requerido pode não ter sido passado no corpo da requisição.")
			})
	public ResponseEntity<Response<UnidadeDto>> saveUnidade(@Valid @RequestBody UnidadeDto unidadeDto,
			BindingResult result) {
		
		Response<UnidadeDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Unidade unidade = new Unidade();
		BeanUtils.copyProperties(unidadeDto, unidade);
		BeanUtils.copyProperties(unidadeDto.getEndereco(), unidade.getEndereco());
		Unidade newUnidade = unidadeService.save(unidade);
		
		BeanUtils.copyProperties(newUnidade, unidadeDto);
		response.setData(unidadeDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	@Operation(
			tags = {"Unidade"})
	public ResponseEntity<Response<UnidadeDto>> updateUnidade(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid UnidadeDto unidadeDto, BindingResult result) {
		
		Response<UnidadeDto> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Unidade> unidadeOptional = unidadeService.findOneById(id);
		if (unidadeOptional.isPresent()) {
			updateUnidadeFields(unidadeDto, unidadeOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		
		Unidade updatedUnidade = unidadeService.save(unidadeOptional.get());
		BeanUtils.copyProperties(updatedUnidade, unidadeDto);
		response.setData(unidadeDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}/endereco")
	@Operation(
			tags = {"Unidade"})
	public ResponseEntity<Response<EnderecoUpdateDto>> updateEnderecoMedico(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EnderecoUpdateDto enderecoUpdateDto, BindingResult result) {
		
		Optional<Unidade> unidadeOptional = unidadeService.findOneById(id);
		if (!unidadeOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
		
		updateEnderecoUnidadeFields(enderecoUpdateDto, unidadeOptional.get().getEndereco());
		
		Endereco updatedEndereco = enderecoService.save(unidadeOptional.get().getEndereco());
		BeanUtils.copyProperties(updatedEndereco, enderecoUpdateDto);
		
		Response<EnderecoUpdateDto> response = new Response<>();
		response.setData(enderecoUpdateDto);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	@Operation(
			tags = {"Unidade"})
	public ResponseEntity<String> deleteUnidade(@PathVariable(name = "id") UUID id) {
		
		Optional<Unidade> unidadeOptional = unidadeService.findOneById(id);
		if (!unidadeOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		unidadeService.delete(unidadeOptional.get());
		
		return ResponseEntity.ok(String.format("Unidade de id=%s excluído com sucesso.", id));
	}
	
	private boolean existeErroDeValidacao(Response<? extends Object> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
	
	private void updateUnidadeFields(UnidadeDto unidadeDto, Unidade unidade) {
		if (unidadeDto.getNome() != null) {
			unidade.setNome(unidadeDto.getNome());
		}
		if (unidadeDto.getTelefone() != null) {
			unidade.setTelefone(unidadeDto.getTelefone());
		}
		if (unidadeDto.getEmail() != null) {
			unidade.setEmail(unidadeDto.getEmail());
		}
	}
	
	private void updateEnderecoUnidadeFields(EnderecoUpdateDto enderecoUpdateDto, Endereco endereco) {
		if (enderecoUpdateDto.getCep() != null) {
			endereco.setCep(enderecoUpdateDto.getCep());
		}
		if (enderecoUpdateDto.getLogradouro() != null) {
			endereco.setLogradouro(enderecoUpdateDto.getLogradouro());
		}
		if (enderecoUpdateDto.getNumero() != null) {
			endereco.setNumero(enderecoUpdateDto.getNumero());
		}
		if (enderecoUpdateDto.getBairro() != null) {
			endereco.setBairro(enderecoUpdateDto.getBairro());
		}
		if (enderecoUpdateDto.getCidade() != null) {
			endereco.setCidade(enderecoUpdateDto.getCidade());
		}
		if (enderecoUpdateDto.getEstado() != null) {
			endereco.setEstado(enderecoUpdateDto.getEstado());
		}
		
	}
}
