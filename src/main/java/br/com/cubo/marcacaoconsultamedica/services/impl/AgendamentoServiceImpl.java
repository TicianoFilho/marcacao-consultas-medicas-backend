package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;
import br.com.cubo.marcacaoconsultamedica.repositories.AgendamentoRepository;
import br.com.cubo.marcacaoconsultamedica.services.AgendamentoService;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {
	
	private final AgendamentoRepository agendamentoRepository;

	public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository) {
		this.agendamentoRepository = agendamentoRepository;
	}

	@Override
	@Transactional
	public Agendamento save(Agendamento agendamento) {
		return agendamentoRepository.save(agendamento);
	}

	@Override
	public Page<Agendamento> findAll(Pageable pageable) {
		return agendamentoRepository.findAll(pageable);
	}

	@Override
	public Optional<Agendamento> findOneById(UUID id) {
		return agendamentoRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Agendamento agendamento) {
		agendamentoRepository.delete(agendamento);		
	}

}
