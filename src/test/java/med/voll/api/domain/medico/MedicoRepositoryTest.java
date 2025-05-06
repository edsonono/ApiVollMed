package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;


@DataJpaTest       //para testar uma interfaceRepository
@AutoConfigureTestDatabase(replace = Replace.NONE) // não substituir as configurações de banco de dados pelo banco H2 em memória.
@ActiveProfiles("test")                            //irá ler o aplication-test.properties que sobrescreve o o aplication.properties
class MedicoRepositoryTest {


	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private TestEntityManager em;  //para efetuar o insert nas tabelas para poder testar

	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
	void testEscolheMedicoAleatorioLivreNaDataCenario1() {  //cenario 1
		//fail("Not yet implemented");

		@NotNull
		@Future
		LocalDateTime proximaSegundaAs10 = LocalDate.now()        //data atual
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))  //proxima segunda feira
				.atTime(10,0);                                   //as 10:00

		var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);  //cadastra o médico

		var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");               //cadastra um paciente

		cadastrarConsulta(medico, paciente, proximaSegundaAs10);                          //cadastra uma consulta do paciente com o medico


		//procura um cardiologista livre, sem consulta marcada na proxima segunda às 10h.
		var medicoLivre = medicoRepository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

		assertThat(medicoLivre).isNull();  //ok, retornou nulo, pois na data o medico ja tem consulta e não esta disponivel.


		//obs: no final do metodo o Spring faz rollback automaticamente.

	}



	@Test
	@DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
	void escolherMedicoAleatorioLivreNaDataCenario2() {     //cenario 2

	     var proximaSegundaAs10 = LocalDate.now()
	                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
	                    .atTime(10, 0);

	    var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);   //cadastra o medico

	    //procura um cardiologista livre, sem consulta marcada na proxima segunda às 10h.
	    var medicoLivre = medicoRepository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

	    assertThat(medicoLivre).isEqualTo(medico);  //ok, retornou o medico recém cadastrado, pois não foi cadastrado uma consulta para o mesmo.

	}



	//******************************************************************************************************
	//metodos apenas para cadastramento


	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, medico, paciente, data,null));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new DadosCadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

	//******************************************************************************************************



}
