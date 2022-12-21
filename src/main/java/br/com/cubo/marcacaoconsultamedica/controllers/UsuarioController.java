package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.UsuarioDto;
import br.com.cubo.marcacaoconsultamedica.entities.login.Role;
import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;
import br.com.cubo.marcacaoconsultamedica.services.RoleService;
import br.com.cubo.marcacaoconsultamedica.services.UsuarioService;

@RestController
@RequestMapping("/api/auth/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;

	public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, RoleService roleService) {
		this.usuarioService = usuarioService;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	@PostMapping
	public ResponseEntity<Object> novoUsuario(@RequestBody UsuarioDto usuarioDto) { 
		if (usuarioService.existsByUsername(usuarioDto.getUsername())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("O usuário %s já existe.", usuarioDto.getUsername()));
		}
		
		usuarioDto.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
		
		Usuario usuario = new Usuario();
		BeanUtils.copyProperties(usuarioDto, usuario);
		
		Role role = roleService.findByDescricao("ROLE_USER").get();
		usuario.setRoles(Collections.singletonList(role));
		
		Usuario novoUsuario = usuarioService.save(usuario); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
		
	}
}
