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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.MedicoDto;
import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.exceptions.ResourceNotFoundException;
import br.com.cubo.marcacaoconsultamedica.services.EnderecoService;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;
import br.com.cubo.marcacaoconsultamedica.services.MedicoService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

	private final MedicoService medicoService;
	private final EnderecoService enderecoService;
	private final EspecialidadeService especialidadeService;

	public MedicoController(MedicoService medicoService, EnderecoService enderecoService, EspecialidadeService especialidadeService) {
		this.medicoService = medicoService;
		this.enderecoService = enderecoService;
		this.especialidadeService = especialidadeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Medico>> getAllMedicos(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(medicoService.findAll(pageable));		
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Object> getMedicoById(@PathVariable(name = "id") UUID id) {
		
		Optional<Medico> medicoOptional = medicoService.findOneById(id);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.MEDICO_NOT_FOUND);
		}
		return ResponseEntity.ok(medicoOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<Response<Medico>> saveMedico(@Valid @RequestBody MedicoDto medicoDto,
			BindingResult result) {
		
		Response<Medico> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Medico medico = new Medico();	
		
		medicoDto.getEspecialidades().forEach(id -> {												// add to entity each especiaildades from dto
			medico.getEspecialidades().add(especialidadeService.findOneById(id)
					.orElseThrow(() -> new ResourceNotFoundException(AppMessages.ESPECIALIDADE_NOT_FOUND)));
		});
				
		BeanUtils.copyProperties(medicoDto, medico);
		BeanUtils.copyProperties(medicoDto.getEndereco(), medico.getEndereco());
		
		medico.setEndereco(enderecoService.save(medico.getEndereco()));
		Medico newMedico = medicoService.save(medico);
		
		response.setData(newMedico);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Medico>> updateMedico(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid MedicoDto medicoDto, BindingResult result) {
		
		// TODO create dto for specific update
		
		Response<Medico> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Medico> medicoOptional = medicoService.findOneById(id);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Medico medico = new Medico();
		BeanUtils.copyProperties(medicoDto, medico);
		medico.setId(medicoOptional.get().getId());
		
		Medico updatedMedico = medicoService.save(medico);
		response.setData(updatedMedico);
		
		return ResponseEntity.ok(response);
	}
	
	private boolean existeErroDeValidacao(Response<Medico> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
}
