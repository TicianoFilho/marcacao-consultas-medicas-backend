package br.com.cubo.marcacaoconsultamedica.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

	public Optional<Usuario> findByUsername(String username);
	public Boolean existsByUsername(String username);
}
