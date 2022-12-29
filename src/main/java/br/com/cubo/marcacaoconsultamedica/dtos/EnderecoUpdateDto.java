package br.com.cubo.marcacaoconsultamedica.dtos;

import lombok.Data;

@Data
public class EnderecoUpdateDto {

	private String cep;
	
	private String logradouro;
	
	private String numero;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
}
