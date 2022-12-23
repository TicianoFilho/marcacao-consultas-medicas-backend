package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;

public interface EspecialidadeService {

	Especialidade save(Especialidade especialidade);
	Page<Especialidade> findAll(Pageable pageable);
	Optional<Especialidade> findOneById(UUID id);
	void delete(Especialidade especialidade);	
}
