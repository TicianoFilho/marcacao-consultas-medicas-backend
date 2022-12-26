package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TipoPlanoDto {

	@NotEmpty(message = "O campo descrição não pode ser vazio.")
	private String descricao;
}
