package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.Especialidade;
import br.com.cubo.marcacaoconsultamedica.repositories.EspecialidadeRepository;
import br.com.cubo.marcacaoconsultamedica.services.EspecialidadeService;

@Service
public class EspecialidadeServiceImpl implements EspecialidadeService {

	private final EspecialidadeRepository especialidadeRepository;
	
	public EspecialidadeServiceImpl(EspecialidadeRepository especialidadeRepository) {
		this.especialidadeRepository = especialidadeRepository;
	}

	@Override
	@Transactional
	public Especialidade save(Especialidade especialidade) {
		return especialidadeRepository.save(especialidade);
	}

	@Override
	public Page<Especialidade> findAll(Pageable pageable) {
		return especialidadeRepository.findAll(pageable);
	}

	@Override
	public Optional<Especialidade> findOneById(UUID id) {
		return especialidadeRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Especialidade especialidade) {
		especialidadeRepository.delete(especialidade);
		
	}

	@Override
	public boolean exists(UUID id) {
		return especialidadeRepository.existsById(id);
	}

}
