package br.com.cubo.marcacaoconsultamedica.dtos;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class PacienteDto {

	@NotEmpty(message = "O nome não pode ser vazio.")
	private String nome;
	
	@CPF
	@NotEmpty(message = "O CPF não pode ser vazio.")
	private String cpf;
	
	@NotEmpty(message = "O telefone não pode ser vazio.")
	private String telefone;
	
	@Email
	@NotEmpty(message = "O email não pode ser vazio.")
	private String email;
	
	@NotNull(message = "O endereço não pode ser vazio.")
	private EnderecoDto endereco;
	
	private boolean ativo;
	
	@NotNull(message = "O Tipo de Plano não pode ser vazio.")
	private UUID tipoPlano;
}
