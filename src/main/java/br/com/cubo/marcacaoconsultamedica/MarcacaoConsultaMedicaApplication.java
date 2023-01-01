package br.com.cubo.marcacaoconsultamedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import br.com.cubo.marcacaoconsultamedica.entities.Agendamento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@SpringBootApplication
public class MarcacaoConsultaMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcacaoConsultaMedicaApplication.class, args);
	}

}
