package br.com.cubo.marcacaoconsultamedica.dtos;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class EnderecoUpdateDto {

	@Pattern(regexp = "[0-9]{5}-[0-9]{3}", message = "Formato do CEP inválido. Ex:(XXXXX-XXX)")
	private String cep;
	
	private String logradouro;
	
	private String numero;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
}
