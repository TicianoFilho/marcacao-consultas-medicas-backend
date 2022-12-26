package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cubo.marcacaoconsultamedica.entities.Endereco;
import br.com.cubo.marcacaoconsultamedica.repositories.EnderecoRepository;
import br.com.cubo.marcacaoconsultamedica.services.EnderecoService;

public class EnderecoServiceImpl implements EnderecoService {

	private final EnderecoRepository enderecoRepository;
	
	public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
	@Transactional
	public Endereco save(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}

	@Override
	public Page<Endereco> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Optional<Endereco> findOneById(UUID id) {
		return enderecoRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(Endereco endereco) {
		enderecoRepository.delete(endereco);
		
	}

}
