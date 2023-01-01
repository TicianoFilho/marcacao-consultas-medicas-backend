package br.com.cubo.marcacaoconsultamedica.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cubo.marcacaoconsultamedica.enums.SituacaoAgendamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "AGENDAMENTO")
public class Agendamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "hora_agendada", nullable = false)
	private LocalTime hora;
	
	@Column(name = "data_agendada", nullable = false)
	private LocalDate data;
	
	@Column(name = "situacao")
	@Enumerated(EnumType.STRING)
	private SituacaoAgendamento situacao;
	
	@ManyToOne
	@JoinColumn(name = "medico_id", referencedColumnName = "id")
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name = "especialidade_id", referencedColumnName = "id")
	private Especialidade especialidade;
	
	@ManyToOne
	@JoinColumn(name = "unidade_id", referencedColumnName = "id")
	private Unidade unidade;
	
	@ManyToOne
	@JoinColumn(name = "paciente_id", referencedColumnName = "id")
	private Paciente paciente;
	
}
