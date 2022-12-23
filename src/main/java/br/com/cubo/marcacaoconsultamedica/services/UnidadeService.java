package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Unidade;

public interface UnidadeService {

	Unidade save(Unidade unidade);
	Page<Unidade> findAll(Pageable pageable);
	Optional<Unidade> findOneById(UUID id);
	void delete(Unidade unidade);	
}
