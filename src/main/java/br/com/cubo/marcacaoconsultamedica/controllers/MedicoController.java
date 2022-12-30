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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import br.com.cubo.marcacaoconsultamedica.entities.Endereco;
import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
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
		
		Medico newMedico = medicoService.save(medico);
		
		response.setData(newMedico);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<MedicoUpdateDto>> updateMedico(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid MedicoUpdateDto medicoUpdateDto, BindingResult result) {
		
		Optional<Medico> medicoOptional = medicoService.findOneById(id);
		if (medicoOptional.isPresent()) {
			updateMedicoFields(medicoUpdateDto, medicoOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
		
		Medico updatedMedico = medicoService.save(medicoOptional.get());
		BeanUtils.copyProperties(updatedMedico, medicoUpdateDto);
		
		Response<MedicoUpdateDto> response = new Response<>();
		response.setData(medicoUpdateDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}/endereco")
	public ResponseEntity<Response<EnderecoUpdateDto>> updateEnderecoMedico(@PathVariable(name = "id") UUID id,
			@RequestBody @Valid EnderecoUpdateDto enderecoUpdateDto, BindingResult result) {
		
		Optional<Medico> medicoOptional = medicoService.findOneById(id);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
		
		updateEnderecoMedicoFields(enderecoUpdateDto, medicoOptional.get().getEndereco());
		
		Endereco updatedEndereco = enderecoService.save(medicoOptional.get().getEndereco());
		BeanUtils.copyProperties(updatedEndereco, enderecoUpdateDto);
		
		Response<EnderecoUpdateDto> response = new Response<>();
		response.setData(enderecoUpdateDto);
		
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMedico(@PathVariable(name = "id") UUID id) {
		
		Optional<Medico> medicoOptional = medicoService.findOneById(id);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		medicoService.delete(medicoOptional.get());
		
		return ResponseEntity.ok(String.format("O médico de id=%s foi excluído com sucesso.", id));
	}
	
	@PutMapping("/{medicoId}/especialidades/{especialidadeId}")
	public ResponseEntity<List<Especialidade>> addEspecialidade(@PathVariable(name = "medicoId") UUID medicoId,
			@PathVariable(name = "especialidadeId") UUID especialidadeId) {
		
		Especialidade especialidade = especialidadeService.findOneById(especialidadeId).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.ESPECIALIDADE_NOT_FOUND));
		
		Optional<Medico> medicoOptional = medicoService.findOneById(medicoId);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
		
		medicoOptional.get().getEspecialidades().add(especialidade);
		Medico updatedMedicoEspecialidade = medicoService.save(medicoOptional.get());
			
		return ResponseEntity.ok(updatedMedicoEspecialidade.getEspecialidades());
	}
	
	@DeleteMapping("/{medicoId}/especialidades/{especialidadeId}")
	public ResponseEntity<List<Especialidade>> deleteEspecialidade(@PathVariable(name = "medicoId") UUID medicoId,
			@PathVariable(name = "especialidadeId") UUID especialidadeId) {
		
		Especialidade especialidade = especialidadeService.findOneById(especialidadeId).orElseThrow(
				() -> new ResourceNotFoundException(AppMessages.ESPECIALIDADE_NOT_FOUND));
		
		Optional<Medico> medicoOptional = medicoService.findOneById(medicoId);
		if (!medicoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		} 
		
		medicoOptional.get().getEspecialidades().remove(especialidade);
		Medico updatedMedicoEspecialidade = medicoService.save(medicoOptional.get());
			
		return ResponseEntity.ok(updatedMedicoEspecialidade.getEspecialidades());
	}
	
	private boolean existeErroDeValidacao(Response<? extends Object> response, BindingResult result) {		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return true;
		}
		return false;
	}
	
	private void updateMedicoFields(MedicoUpdateDto medicoUpdateDto, Medico medico) {
		if (medicoUpdateDto.getNome() != null) {
			medico.setNome(medicoUpdateDto.getNome());
		}
		if (medicoUpdateDto.getCpf() != null) {
			medico.setCpf(medicoUpdateDto.getCpf());
		}
		if (medicoUpdateDto.getEmail() != null) {
			medico.setEmail(medicoUpdateDto.getEmail());
		}
		if (medicoUpdateDto.getCrm() != null) {
			medico.setCrm(medicoUpdateDto.getCrm());
		}
		if (medicoUpdateDto.getTelefone() != null) {
			medico.setTelefone(medicoUpdateDto.getTelefone());
		}
	}
	
	private void updateEnderecoMedicoFields(EnderecoUpdateDto enderecoUpdateDto, Endereco endereco) {
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
