package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;
import br.com.cubo.marcacaoconsultamedica.services.AgendamentoService;

public class AgendamentoServiceImpl implements AgendamentoService {

	@Override
	public Agendamento save(Agendamento agendamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Agendamento> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Agendamento> findOneById(UUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Agendamento agendamento) {
		// TODO Auto-generated method stub
		
	}

}
