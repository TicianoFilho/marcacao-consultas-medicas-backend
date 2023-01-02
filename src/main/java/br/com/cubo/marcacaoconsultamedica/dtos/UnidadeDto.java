package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UnidadeDto {

	@NotBlank(message = "O nome não pode ser vazio.")
	private String nome;
	
	@Pattern(regexp = "^\\([0-9]{2}\\) [0-9]?[0-9]{5}-[0-9]{4}$", message = "Formato do tefelone inválido. Ex: (xx) xxxxx-xxxx")
	@NotBlank(message = "O telefone não pode ser vazio.")
	private String telefone;
	
	@Email(message = "Email inválido.")
	@NotBlank(message = "O email não pode ser vazio.")
	private String email;
	
	@NotNull(message = "O objeto endereço não pode ser vazio.")
	private EnderecoDto endereco;
}
