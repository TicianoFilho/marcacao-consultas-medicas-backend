package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Medico;
import br.com.cubo.marcacaoconsultamedica.repositories.MedicoRepository;
import br.com.cubo.marcacaoconsultamedica.services.MedicoService;

public class MedicoServiceImpl implements MedicoService {

	private final MedicoRepository medicoRepository;
	
	public MedicoServiceImpl(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}

	@Override
	@Transactional
	public Medico save(Medico medico) {
		return medicoRepository.save(medico);
	}

	@Override
	public Page<Medico> findAll(Pageable pageable) {
		return medicoRepository.findAll(pageable);
	}

	@Override
	public Optional<Medico> findOneById(UUID id) {
		return medicoRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Medico medico) {
		medicoRepository.delete(medico);
		
	}

}
