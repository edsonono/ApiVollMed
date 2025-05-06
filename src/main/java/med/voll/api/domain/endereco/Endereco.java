package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable                   //anotação da JPA     - indica que faz parte da tabela medicos
@Getter                       //anotação do Lombok  - cria os getters
@NoArgsConstructor            //anotação do Lombok  - cria o construtor sem argumentos
@AllArgsConstructor           //anotação do Lombok  - cria o contrutor com todos os argumentos
public class Endereco {



	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String cidade;
	private String uf;

	public Endereco(DadosEndereco endereco) {

		this.logradouro  = endereco.logradouro();
		this.numero      = endereco.numero();
		this.complemento = endereco.complemento();
		this.cep         = endereco.cep();
		this.bairro      = endereco.bairro();
		this.cidade      = endereco.cidade();
		this.uf          = endereco.uf();

	}


	public void atualizarInformacoes(DadosEndereco dados) {

		if (dados.logradouro() != null ) {
			this.logradouro = dados.logradouro();
		}

		if (dados.numero() != null ) {
			this.numero = dados.numero();
		}

		if (dados.complemento() != null ) {
			this.complemento = dados.complemento();
		}

		if (dados.cep() != null ) {
			this.cep = dados.cep();
		}

		if (dados.bairro() != null ) {
			this.bairro = dados.bairro();
		}

		if (dados.cidade() != null ) {
			this.cidade = dados.cidade();
		}

		if (dados.uf() != null ) {
			this.uf = dados.uf();
		}

	}



}
