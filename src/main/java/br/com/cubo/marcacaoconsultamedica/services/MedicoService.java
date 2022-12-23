package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Medico;

public interface MedicoService {

	Medico save(Medico medico);
	Page<Medico> findAll(Pageable pageable);
	Optional<Medico> findOneById(UUID id);
	void delete(Medico medico);	
}
