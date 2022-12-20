package br.com.cubo.marcacaoconsultamedica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<UnidadeRepository, UUID> {

}
