package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;

@RestControllerAdvice                //anotação para que o Spring entenda que é uma classe para tratar erros da api e carregar automaticamente
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404(){
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){

		var erros = ex.getFieldErrors();
		//return ResponseEntity.badRequest().body(erros);  //.body já devolve um ResponseEntity
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());  //Recurso do java 8 que converte uma lista para outra.
	}

	@ExceptionHandler(ValidacaoException.class)                               //Necessario tratar o retorno da api em caso de exception da regra de negócio
	public ResponseEntity tratarErroRegraDeNegocio( ValidacaoException ex){   //Recebe a exception
		return ResponseEntity.badRequest().body(ex.getMessage());             //Retorna erro 400 e a mensagem
	}

	private record DadosErrosValidacao(String campo, String mensagem) {  //record contendo apenas o campo e a mensagem e criado internamente.

		public DadosErrosValidacao(FieldError erro) {

			this(erro.getField(), erro.getDefaultMessage());  //joga o campo e a mensagem para o construtor principal campo e mensagem.
		}
	}

}
