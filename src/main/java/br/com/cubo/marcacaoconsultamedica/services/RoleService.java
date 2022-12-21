package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;

import br.com.cubo.marcacaoconsultamedica.entities.login.Role;

public interface RoleService {

	Optional<Role> findByDescricao(String descricao);
}
