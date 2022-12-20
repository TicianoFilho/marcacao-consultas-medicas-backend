package br.com.cubo.marcacaoconsultamedica.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cubo.marcacaoconsultamedica.entities.login.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Optional<Role> findByDescricao(String descricao);
}
