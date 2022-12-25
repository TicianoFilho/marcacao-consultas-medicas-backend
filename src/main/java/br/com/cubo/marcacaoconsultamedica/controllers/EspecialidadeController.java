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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.EspecialidadeDto;
import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

	private final EspecialidadeService especialidadeService;

	public EspecialidadeController(EspecialidadeService especialidadeService) {
		this.especialidadeService = especialidadeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Especialidade>> getAllEspecialidades(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) throws Exception {
		return ResponseEntity.ok().body(especialidadeService.findAll(pageable));		
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
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Especialidade especialidade = new Especialidade();
		BeanUtils.copyProperties(especialidadeDto, especialidade);
		Especialidade newEspecialidade = especialidadeService.save(especialidade);
		response.setData(newEspecialidade);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	
}
