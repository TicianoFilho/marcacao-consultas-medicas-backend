package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;

public interface AgendamentoService {

	Agendamento save(Agendamento agendamento);
	Page<Agendamento> findAll(Pageable pageable);
	Optional<Agendamento> findOneById(UUID id);
	void delete(Agendamento agendamento);
}
