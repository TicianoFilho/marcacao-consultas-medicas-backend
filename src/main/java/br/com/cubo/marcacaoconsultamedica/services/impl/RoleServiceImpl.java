package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.login.Role;
import br.com.cubo.marcacaoconsultamedica.repositories.RoleRepository;
import br.com.cubo.marcacaoconsultamedica.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Optional<Role> findByDescricao(String descricao) { 
		return roleRepository.findByDescricao(descricao);
	}

	
}
