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

import br.com.cubo.marcacaoconsultamedica.dtos.EnderecoUpdateDto;
import br.com.cubo.marcacaoconsultamedica.dtos.MedicoDto;
import br.com.cubo.marcacaoconsultamedica.dtos.MedicoUpdateDto;
import br.com.cubo.marcacaoconsultamedica.dtos.PacienteDto;
import br.com.cubo.marcacaoconsultamedica.dtos.PacienteUpdateDto;
import br.com.cubo.marcacaoconsultamedica.entities.Endereco;
import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.entities.Paciente;
import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.exceptions.ResourceNotFoundException;
import br.com.cubo.marcacaoconsultamedica.services.EnderecoService;
import br.com.cubo.marcacaoconsultamedica.services.PacienteService;
import br.com.cubo.marcacaoconsultamedica.services.TipoPlanoService;
import br.com.cubo.marcacaoconsultamedica.utils.AppMessages;
import br.com.cubo.marcacaoconsultamedica.utils.Response;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

	private final PacienteService pacienteService;
	private final TipoPlanoService tipoPlanoService;
	private final EnderecoService enderecoService;

	public PacienteController(PacienteService pacienteService, TipoPlanoService tipoPlanoService,
			EnderecoService enderecoService) {
		this.pacienteService = pacienteService;
		this.tipoPlanoService = tipoPlanoService;
		this.enderecoService = enderecoService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Paciente>> getAllPacientes(
			@PageableDefault(page = 0, size = 5, sort = "id", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(pacienteService.findAll(pageable));		
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Object> getPacienteById(@PathVariable(name = "id") UUID id) {
		
		Optional<Paciente> pacienteOptional = pacienteService.findOneById(id);
		if (!pacienteOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AppMessages.PACIENTE_NOT_FOUND);
		}
		return ResponseEntity.ok(pacienteOptional.get());
	}
	
	@PostMapping
	public ResponseEntity<Response<Paciente>> savePaciente(@Valid @RequestBody PacienteDto pacienteDto,
			BindingResult result) {
		
		Response<Paciente> response = new Response<>();
		if (existeErroDeValidacao(response, result)) {
			return ResponseEntity.badRequest().body(response);
		}
		
		TipoPlano tipoPlano = tipoPlanoService.findOneById(pacienteDto.getTipoPlano()).orElseThrow(
				()-> new ResourceNotFoundException("Tipo de Plano não existe."));
		
		Paciente paciente = new Paciente();				
		BeanUtils.copyProperties(pacienteDto, paciente);
		BeanUtils.copyProperties(pacienteDto.getEndereco(), paciente.getEndereco());
		paciente.setTipoPlano(tipoPlano);
		
		Paciente newPaciente = pacienteService.save(paciente);		
		response.setData(newPaciente);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<PacienteUpdateDto>> updatePaciente(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid PacienteUpdateDto pacienteUpdateDto, BindingResult result) {
		
		Response<PacienteUpdateDto> response = new Response<>();	
		Paciente paciente = null;
		
		if (!existeErroDeValidacao(response, result)) {
			paciente = pacienteService.findOneById(id).orElseThrow(
					() -> new ResourceNotFoundException(AppMessages.PACIENTE_NOT_FOUND));
		} else {
			return ResponseEntity.badRequest().body(response);
		}
		
		updatePacienteFields(pacienteUpdateDto, paciente);
		
		Paciente updatedPaciente = pacienteService.save(paciente);
		BeanUtils.copyProperties(updatedPaciente, pacienteUpdateDto);
			
		response.setData(pacienteUpdateDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}/endereco")
	public ResponseEntity<Response<EnderecoUpdateDto>> updateEnderecoPaciente(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EnderecoUpdateDto enderecoUpdateDto, BindingResult result) {
		
		Response<EnderecoUpdateDto> response = new Response<>();	
		Paciente paciente = null;
		
		if (!existeErroDeValidacao(response, result)) {
			paciente = pacienteService.findOneById(id).orElseThrow(
					() -> new ResourceNotFoundException(AppMessages.PACIENTE_NOT_FOUND));
		} else {
			return ResponseEntity.badRequest().body(response);
		}
		
		updateEnderecoPacienteFields(enderecoUpdateDto, paciente.getEndereco());
		
		Endereco updatedEndereco = enderecoService.save(paciente.getEndereco());
		BeanUtils.copyProperties(updatedEndereco, enderecoUpdateDto);
		
		response.setData(enderecoUpdateDto);
		
		return ResponseEntity.ok(response);
	}
	
	private boolean existeErroDeValidacao(Response<? extends Object> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
	
	private void updatePacienteFields(PacienteUpdateDto pacienteUpdateDto, Paciente paciente) {
		if (pacienteUpdateDto.getNome() != null) {
			paciente.setNome(pacienteUpdateDto.getNome());
		}
		if (pacienteUpdateDto.getCpf() != null) {
			paciente.setCpf(pacienteUpdateDto.getCpf());
		}
		if (pacienteUpdateDto.getEmail() != null) {
			paciente.setEmail(pacienteUpdateDto.getEmail());
		}
		if (pacienteUpdateDto.getTelefone() != null) {
			paciente.setTelefone(pacienteUpdateDto.getTelefone());
		}
	}
	
	private void updateEnderecoPacienteFields(EnderecoUpdateDto enderecoUpdateDto, Endereco endereco) {
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
