package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class EnderecoDto {

	@NotEmpty(message = "O CEP não pode ser vazio.")
	@Pattern(regexp = "[0-9]{5}-[0-9]{3}")
	private String cep;
	
	@NotEmpty(message = "O logradouro não pode ser vazio.")
	private String logradouro;
	
	@NotEmpty(message = "O número não pode ser vazio.")
	private String numero;
	
	@NotEmpty(message = "O bairro não pode ser vazio.")
	private String bairro;
	
	@NotEmpty(message = "A cidade não pode ser vazia.")
	private String cidade;
	
	@NotEmpty(message = "O estado não pode ser vazio.")
	private String estado;
		
}
