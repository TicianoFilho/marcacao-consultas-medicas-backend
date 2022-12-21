package br.com.cubo.marcacaoconsultamedica.services;

import java.util.Optional;

import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;

public interface UsuarioService {

	Optional<Usuario> findByUsername(String username);
	Boolean existsByUsername(String username);
	Usuario save(Usuario usuario);
}
