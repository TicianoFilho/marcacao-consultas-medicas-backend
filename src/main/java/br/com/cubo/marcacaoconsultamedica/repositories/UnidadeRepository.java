package br.com.cubo.marcacaoconsultamedica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cubo.marcacaoconsultamedica.entities.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, UUID> {

}
