package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;

public class EspecialidadeServiceImpl implements EspecialidadeService {

	@Override
	public Especialidade save(Especialidade especialidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Especialidade> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Especialidade> findOneById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Especialidade especialidade) {
		// TODO Auto-generated method stub
		
	}

}
