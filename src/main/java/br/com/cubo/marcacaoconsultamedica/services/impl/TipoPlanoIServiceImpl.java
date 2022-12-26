package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;
import br.com.cubo.marcacaoconsultamedica.repositories.TipoPlanoRepository;
import br.com.cubo.marcacaoconsultamedica.services.TipoPlanoService;

@Service
public class TipoPlanoIServiceImpl implements TipoPlanoService {

	private final TipoPlanoRepository tipoPlanoRepository;
	
	public TipoPlanoIServiceImpl(TipoPlanoRepository tipoPlanoRepository) {
		this.tipoPlanoRepository = tipoPlanoRepository;
	}

	@Override
	@Transactional
	public TipoPlano save(TipoPlano tipoPlano) {
		return tipoPlanoRepository.save(tipoPlano);
	}

	@Override
	public Page<TipoPlano> findAll(Pageable pageable) {
		return tipoPlanoRepository.findAll(pageable);
	}

	@Override
	public Optional<TipoPlano> findOneById(UUID id) {
		return tipoPlanoRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(TipoPlano tipoPlano) {
		tipoPlanoRepository.delete(tipoPlano);
		
	}

}
