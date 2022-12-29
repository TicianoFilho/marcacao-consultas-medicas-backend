package br.com.cubo.marcacaoconsultamedica.services.impl;

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
		return unidadeRepository.save(unidade);
	}

	@Override
	public Page<Unidade> findAll(Pageable pageable) {
		return unidadeRepository.findAll(pageable);
	}

	@Override
	public Optional<Unidade> findOneById(UUID id) {
		return unidadeRepository.findById(id);
	}

	@Override
	public void delete(Unidade unidade) {
		unidadeRepository.delete(unidade);
		
	}

	
}
