package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;                 //apesar de ser uma classe do Spring, é necessário configurar no SecurityConfiguration

	@Autowired
	private TokenService tokenService;                    //implmentando o retorno do token

	@PostMapping
	public ResponseEntity efetuarLogin (@RequestBody @Valid DadosAutenticacao dados) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

		var authentication = manager.authenticate(authenticationToken);  //como não posso passar o usuario e senha diretamente, é passado o token

		//return ResponseEntity.ok().build();
//		return ResponseEntity.ok(tokenService.gerarToken((Usuario) authentication.getPrincipal())); //devolver um token no corpo

	    var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

	    return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}

}
