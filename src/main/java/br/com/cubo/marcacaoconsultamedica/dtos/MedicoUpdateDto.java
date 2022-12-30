package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class MedicoUpdateDto {

	private String nome;
	
	@CPF(message = "O CPF informado é inválido.")
	private String cpf;
	
	@Pattern(regexp = "^\\([0-9]{2}\\) [0-9]?[0-9]{5}-[0-9]{4}$", message = "Formato do tefelone inválido.")
	private String telefone;
	
	@Email(message = "O email informado é inválido.")
	private String email;
	
	private String crm;
	
}
