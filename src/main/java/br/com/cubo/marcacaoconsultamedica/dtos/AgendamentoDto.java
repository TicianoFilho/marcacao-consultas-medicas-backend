package br.com.cubo.marcacaoconsultamedica.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AgendamentoDto {

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora;
	
//	@Pattern(regexp = "^(?i)(EM_ABERTO | CANCELADO | FINALIZADO)$", 
//	message = "Apenas \"EM_ABERTO\", \"CANCELADO\", \"FINALIZADO\".")
//	private String situacao;
	
	private UUID pacienteId;
	
	private UUID medicoId;
	
	private UUID unidadeId;
	
	private UUID especialidadeId;
}
