package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UnidadeDto {

	private String nome;
	
	@Pattern(regexp = "^\\([0-9]{2}\\) [0-9]?[0-9]{5}-[0-9]{4}$", message = "Formato do tefelone inválido. Ex: (xx) xxxxx-xxxx")
	private String telefone;
	
	@Email(message = "Email inválido.")
	private String email;
	
	private EnderecoDto endereco;
}
