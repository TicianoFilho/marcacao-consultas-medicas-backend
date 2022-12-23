package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.Unidade;
import br.com.cubo.marcacaoconsultamedica.repositories.UnidadeRepository;
import br.com.cubo.marcacaoconsultamedica.services.UnidadeService;

@Service
public class UnidadeServiceImpl implements UnidadeService {

	private final UnidadeRepository unidadeRepository;

	public UnidadeServiceImpl(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}

	@Override
	public Unidade save(Unidade unidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Unidade> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Unidade> findOneById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}

	
}
