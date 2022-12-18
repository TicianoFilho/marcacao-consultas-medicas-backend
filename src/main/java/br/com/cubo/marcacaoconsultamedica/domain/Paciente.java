package br.com.cubo.marcacaoconsultamedica.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "PACIENTE")
public class Paciente extends Pessoa {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_plano_id", referencedColumnName = "id")
	private TipoPlano tipoPlano;
	
	@Column(nullable = false)
	private boolean ativo = false;
}
