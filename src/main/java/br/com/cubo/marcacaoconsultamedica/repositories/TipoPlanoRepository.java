package br.com.cubo.marcacaoconsultamedica.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cubo.marcacaoconsultamedica.entities.TipoPlano;

public interface TipoPlanoRepository extends JpaRepository<TipoPlano, UUID> {

}
