package br.com.cubo.marcacaoconsultamedica.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cubo.marcacaoconsultamedica.entities.login.Role;
import br.com.cubo.marcacaoconsultamedica.entities.login.Usuario;
import br.com.cubo.marcacaoconsultamedica.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("Usuário %S não encontrado.", username)));
		return new User(usuario.getUsername(), usuario.getPassword(), mapRolesToAuthorities(usuario.getRoles())); 
	}

	// turn List<Roles> into Collection<grantedAuthority> 
	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getDescricao())).collect(Collectors.toList());
	}
	
}
