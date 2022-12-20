package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.List;

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
	public List<Unidade> unidades() {
		return unidadeRepository.findAll();
	}

}
