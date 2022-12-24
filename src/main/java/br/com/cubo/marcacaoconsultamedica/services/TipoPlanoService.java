package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;

public interface TipoPlanoService {

	TipoPlano save(TipoPlano tipoPlano);
	Page<TipoPlano> findAll(Pageable pageable);
	Optional<TipoPlano> findOneById(UUID id);
	void delete(TipoPlano tipoPlano);	
}
