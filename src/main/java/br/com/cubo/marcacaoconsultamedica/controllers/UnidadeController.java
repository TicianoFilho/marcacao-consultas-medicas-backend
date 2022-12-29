package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.TipoPlanoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UnidadeDto;
import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeController {

	private final UnidadeService unidadeService;

	public UnidadeController(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	@GetMapping
	public ResponseEntity<Page<Unidade>> getAllUnidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(unidadeService.findAll(pageable));		
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Object> getUnidadeById(@PathVariable(name = "id") UUID id) {
		
		Optional<Unidade> unidadeOptional = unidadeService.findOneById(id);
		if (!unidadeOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.UNIDADE_NOT_FOUND);
		}
		return ResponseEntity.ok(unidadeOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<Response<Unidade>> saveUnidade(@Valid @RequestBody UnidadeDto unidadeDto,
			BindingResult result) {
		
		Response<Unidade> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Unidade unidade = new Unidade();
		BeanUtils.copyProperties(unidadeDto, unidade);
		BeanUtils.copyProperties(unidadeDto.getEndereco(), unidade.getEndereco());
		Unidade newUnidade = unidadeService.save(unidade);
		response.setData(newUnidade);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
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
}
