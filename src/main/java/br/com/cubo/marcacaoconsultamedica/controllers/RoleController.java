package br.com.cubo.marcacaoconsultamedica.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cubo.marcacaoconsultamedica.dtos.RoleDto;
import br.com.cubo.marcacaoconsultamedica.entities.login.Role;
import br.com.cubo.marcacaoconsultamedica.services.RoleService;

@RestController
@RequestMapping("/api/auth/roles")
public class RoleController {

	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping
	public ResponseEntity<List<Role>> findAll() {
		return ResponseEntity.ok(roleService.findAll()); 
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody RoleDto roleDto) {
		Optional<Role> roleOptional = roleService.findByDescricao(roleDto.getDescricao());
		if (roleOptional.isPresent()) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("A role %s já existe.", roleDto.getDescricao()));
		}
		
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		Role newRole = roleService.save(role);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
	}
}
