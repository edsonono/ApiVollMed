package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component      //Para o Spring carregar automaticamente. Não é controller, nem servlet, nem repository, nem configuração, trata-se de um componente genérico
public class SecurityFilter extends OncePerRequestFilter {          //OncePerRequestFilter: para cada requisição a classe é invocada.


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//System.out.println("Filtro chamado"); //acionado após executar a requisição no Insomnia e exibido no Console sem prosseguir.
        //filterChain.doFilter(request, response);  //prossegue com o fluxo.

		//Token vem do cabeçalho Authorization/ Bearer Token

		var tokenJWT = recuperarToken(request);  //receber no DTO

        if (tokenJWT != null) {  //login pode retornar nulo, pois ainda não esta autenticado. então apenas se tiver token na requisição
			var subject = tokenService.getSubject(tokenJWT);
			System.out.println(subject);

            //fazer uma autenticação forçada para não exibir forbidden

			var usuario = repository.findByLogin(subject);  //busca o objeto mediante email recuperado pelo tokenService

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());  //método da classe Usuario

            SecurityContextHolder.getContext().setAuthentication(authentication);  //forçar a autenticação, pois foi localizado.


        }
		//System.out.println(tokenJWT);    //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEuc291emFAdm9sbC5tZWQiLCJpc3MiOiJBUEkgVm9sbC5tZWQiLCJleHAiOjE3NDIzMTMzMzl9.ICsm5wuZNZfQNIVUGg0M8_rrb4YtKnA46if2IYqfvs8
		filterChain.doFilter(request, response);  //prossegue com o fluxo.

	}

private String recuperarToken(HttpServletRequest request) {         //devolve uma String

	var authorizationHeader = request.getHeader("Authorization");    //Authorization com th, é o nome do cabeçalho

    if (authorizationHeader != null) {                              //se login pode estar nulo

        return authorizationHeader.replace("Bearer ", "").trim();   //Rerirar o prefixo Bearer e espaço em branco.
    }

    return null;

}

}
