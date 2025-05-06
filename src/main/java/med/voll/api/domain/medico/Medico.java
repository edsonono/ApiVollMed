package med.voll.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "medicos")      //anotação da JPA
@Entity(name = "Medico")      //anotação da JPA

@Getter                       //anotação do Lombok  - cria os getters
@NoArgsConstructor            //anotação do Lombok  - cria o construtor sem argumentos
@AllArgsConstructor           //anotação do Lombok  - cria o contrutor com todos os argumentos
@EqualsAndHashCode(of = "id") //anotação do Lombok  - cria o equals e o hashcode
public class Medico {

	public Medico(DadosCadastroMedico dados) {

		this.nome          = dados.nome();
		this.email         = dados.email();
		this.telefone      = dados.telefone();              //incluído novo campo
		this.crm           = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco      = new Endereco (dados.endereco());
		this.ativo         = true;                             //já inicializo com true já que se trata de um cadastramento.
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)    //anotação da JPA
	private Long id;
	private String nome;
	private String email;
	private String telefone;	                              //incluído novo campo
	private String crm;
	@Enumerated(EnumType.STRING)                              //anotação da JPA
	private Especialidade especialidade;
	@Embedded                                                 //anotação da JPA   - indica que a classe Endereco faz parte da tabela medicos
	private Endereco endereco;
	private Boolean ativo;                                    //atributo que indica se médico está ativo ou não para utilizar em exclusão lógica.


	public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {

		if (dados.nome() != null) {
			this.nome = dados.nome();
		}

		if (dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if (dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());  //como endereco é um objeto, posso criar um método na classe Endereco e mandar o objeto dados.endereco().
		}

	}


	public void excluir() {

		this.ativo = false;

	}



}
