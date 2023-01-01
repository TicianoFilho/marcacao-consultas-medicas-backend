package br.com.cubo.marcacaoconsultamedica.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	protected String nome;
	
	@Column(nullable = false)
	protected String cpf;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
	protected Endereco endereco;
	
	protected String telefone;
	
	protected String email;
}
