package br.com.cubo.marcacaoconsultamedica.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class MedicoDto {
	
	@NotEmpty(message = "O nome não pode ser vazio.")
	private String nome;
	
	@CPF
	@NotEmpty(message = "O CPF não pode ser vazio.")
	private String cpf;
	
	@NotNull(message = "O endereço não pode ser vazio.")
	private EnderecoDto endereco;
	
	@NotEmpty(message = "O telefone não pode ser vazio.")
	private String telefone;
	
	@Email
	@NotEmpty(message = "O email não pode ser vazio.")
	private String email;
	
	@NotEmpty(message = "O CRM não pode ser vazio.")
	private String crm;
	
	@NotNull 
	private List<UUID> especialidades;

}
