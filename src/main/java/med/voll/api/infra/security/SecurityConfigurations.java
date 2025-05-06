package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration                           //indica que é uma classe de configuração
@EnableWebSecurity                       //idica que as configurações de segurança do Spring serão personalizadas
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception{

//		return http.csrf(csrf -> csrf.disable())                                                    //não faz mais o bloqueio
//				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //não abre mais o formulario de login padrão.
//				.build();                        //desabilitar a proteção de cross-site-request-forgery pois o token já tem esta finalidade.


	        return http.csrf(csrf -> csrf.disable())                                                    //não faz mais o bloqueio
	        		.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //não abre mais o formulario de login padrão.
	        		.authorizeHttpRequests(authorize -> authorize                                       //configurar a autorização
	                        .requestMatchers(HttpMethod.POST, "/login").permitAll()                     //POST e /login liberar sem autenticacao
	                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() //permite documentacao SPring
	                        .anyRequest().authenticated())                                              //qualquer outra requisição, deve autenticar.
	                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	                 //   primeiro invoca o filtro personalizado para depois invocar o filtro do Spring.
	                .build();

            //documentação
	        //http://localhost:8080/v3/api-docs
	        //localhost:8080/swagger-ui/index.html
	}

	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
		//Retorna AuthenticationManager e recebe AuthenticationConfiguration

		return configuration.getAuthenticationManager();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {  //define para o Spring utilizar este hashing de senha.

		return new BCryptPasswordEncoder();    //classe do Spring, mas é possivel instanciar normalmente.
	}
}
