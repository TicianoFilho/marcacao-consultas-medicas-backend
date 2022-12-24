package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Paciente;

public interface PacienteService {

	Paciente save(Paciente ppaciente);
	Page<Paciente> findAll(Pageable pageable);
	Optional<Paciente> findOneById(UUID id);
	void delete(Paciente paciente);	
}
