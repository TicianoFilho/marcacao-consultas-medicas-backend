package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class MedicoUpdateDto {

	private String nome;
	
	@CPF(message = "O CPF informado é inválido.")
	private String cpf;
	
	private String telefone;
	
	@Email(message = "O email informado é inválido.")
	private String email;
	
	private String crm;
	
}
