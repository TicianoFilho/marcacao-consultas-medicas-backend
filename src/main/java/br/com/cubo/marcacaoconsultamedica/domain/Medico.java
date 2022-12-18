package br.com.cubo.marcacaoconsultamedica.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MEDICO")
public class Medico extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false)
	private String crm;
	
	@ManyToMany
	@JoinTable(
			name = "medicos_especialidades",
			joinColumns = @JoinColumn(name = "medico_id"),
			inverseJoinColumns = @JoinColumn(name = "especialidade_id"))
	private List<Especialidade> especialidades;
}
