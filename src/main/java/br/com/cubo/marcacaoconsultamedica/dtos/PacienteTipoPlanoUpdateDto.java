package br.com.cubo.marcacaoconsultamedica.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PacienteTipoPlanoUpdateDto {

	@NotNull(message = "O ID do Tipo de Plano não pode ser vazio.")
	private UUID tipoPlanoId;
}
