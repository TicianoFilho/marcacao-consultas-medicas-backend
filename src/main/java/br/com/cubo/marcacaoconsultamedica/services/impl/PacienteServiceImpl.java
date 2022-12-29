package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.Paciente;
import br.com.cubo.marcacaoconsultamedica.repositories.PacienteRepository;
import br.com.cubo.marcacaoconsultamedica.services.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService {
	
	private final PacienteRepository pacienteRepository;

	public PacienteServiceImpl(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	@Override
	@Transactional
	public Paciente save(Paciente paciente) {
		return pacienteRepository.save(paciente);
	}

	@Override
	public Page<Paciente> findAll(Pageable pageable) {
		return pacienteRepository.findAll(pageable);
	}

	@Override
	public Optional<Paciente> findOneById(UUID id) {
		return pacienteRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Paciente paciente) {
		pacienteRepository.delete(paciente);
		
	}

}
