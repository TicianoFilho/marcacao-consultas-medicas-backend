package br.com.cubo.marcacaoconsultamedica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
		
		return http.build();
	}
	
	@Bean
	UserDetailsService users() {
		UserDetails admin = User.builder()
				.username("Theo")
				.password("abc123")
				.roles("ADMIN")
				.build();
		UserDetails user = User.builder()
				.username("Ticiano")
				.password("123abc")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(admin, user);
	}
}
