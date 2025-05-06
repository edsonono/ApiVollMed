package med.voll.api.domain;

public class ValidacaoException extends RuntimeException {

	public ValidacaoException(String mensagem) { //Recebe a msg no construtor.

		super(mensagem);                         //Executa o construtor base da RuntimeException

	}

}
