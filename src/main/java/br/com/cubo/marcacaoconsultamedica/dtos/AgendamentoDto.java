package br.com.cubo.marcacaoconsultamedica.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AgendamentoDto {

	private UUID id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	private UUID pacienteId;
	
	private UUID medicoId;
	
	private UUID unidadeId;
	
	private UUID especialidadeId;
}
