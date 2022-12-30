package br.com.cubo.marcacaoconsultamedica.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class MedicoDto {
	
	@NotEmpty(message = "O nome não pode ser vazio.")
	private String nome;
	
	@CPF(message = "CPF inválido.")
	@NotEmpty(message = "O CPF não pode ser vazio.")
	private String cpf;
	
	@NotEmpty(message = "O telefone não pode ser vazio.")
	@Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})", message = "Formato do tefelone inválido.")
	private String telefone;
	
	@Email(message = "Email inválido.")
	@NotEmpty(message = "O email não pode ser vazio.")
	private String email;
	
	@NotEmpty(message = "O CRM não pode ser vazio.")
	private String crm;
	
	@NotNull(message = "O endereço não pode ser vazio.")
	private EnderecoDto endereco;
	
	@NotNull 
	private List<UUID> especialidades;

}
