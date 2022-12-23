package br.com.cubo.marcacaoconsultamedica.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;
import br.com.cubo.marcacaoconsultamedica.repositories.UsuarioRepository;
import br.com.cubo.marcacaoconsultamedica.services.UsuarioService;

@Service
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

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

}
