package br.com.cubo.marcacaoconsultamedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Marcação de Consultas Médicas",
				version = "1.0.0",
				description = "A aplicação realiza agendamentos/marcações de consultas médicas"),
		servers = @Server(url = "http://localhost:8080"),
		tags = {@Tag(name = "Unidade", description = "Local onde será realizado o atendimento médico agendado.")})
@SecurityScheme(name = "BearerJWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Gera o token para o projeto.")
public class MarcacaoConsultaMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarcacaoConsultaMedicaApplication.class, args);
	}

}
