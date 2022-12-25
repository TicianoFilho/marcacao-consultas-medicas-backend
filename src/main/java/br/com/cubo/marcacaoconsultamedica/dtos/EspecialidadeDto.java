package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class EspecialidadeDto {

	@NotEmpty
	private String descricao;
}
