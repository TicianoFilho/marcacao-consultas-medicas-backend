package br.com.cubo.marcacaoconsultamedica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.AuthResponseDto;
import br.com.cubo.marcacaoconsultamedica.dtos.UsuarioDto;
import br.com.cubo.marcacaoconsultamedica.security.JWTGenerator;

@RestController
@RequestMapping("/api/auth/login")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JWTGenerator jwtGenerator;

	public AuthController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping
	public ResponseEntity<AuthResponseDto> login(@RequestBody UsuarioDto usuarioDto) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usuarioDto.getUsername(), usuarioDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return ResponseEntity.ok(new AuthResponseDto(token)); 
	}
}
