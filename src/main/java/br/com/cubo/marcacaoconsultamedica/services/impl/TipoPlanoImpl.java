package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.services.TipoPlanoService;

public class TipoPlanoImpl implements TipoPlanoService {

	@Override
	public TipoPlano save(TipoPlano tipoPlano) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<TipoPlano> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TipoPlano> findOneById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(TipoPlano tipoPlano) {
		// TODO Auto-generated method stub
		
	}

}
