package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Endereco;

public interface EnderecoService {

	Endereco save(Endereco endereco);
	Page<Endereco> findAll(Pageable pageable);
	Optional<Endereco> findOneById(UUID id);
	void delete(Endereco endereco);	
}
