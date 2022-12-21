package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;

import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;
import br.com.cubo.marcacaoconsultamedica.repositories.UsuarioRepository;
import br.com.cubo.marcacaoconsultamedica.services.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Optional<Usuario> findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public Boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

}
