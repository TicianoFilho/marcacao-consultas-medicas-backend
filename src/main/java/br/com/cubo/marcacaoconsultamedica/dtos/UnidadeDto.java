package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UnidadeDto {

	private String nome;
	
	@Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})", message = "Formato do tefelone inválido.")
	private String telefone;
	
	@Email
	private String email;
	
	private EnderecoDto endereco;
}
