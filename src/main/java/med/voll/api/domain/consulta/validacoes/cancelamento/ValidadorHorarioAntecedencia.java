package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component("ValidadorHorarioAntecedenciaCancelamento")     //se 2 classes com o mesmo nome em pacotes diferentes, diferenciar pelo @Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

	@Override
	public void validar(DadosCancelamentoConsulta dados) {

		var horarioAgora = LocalDateTime.now();

		var consulta = repository.getReferenceById(dados.idConsulta());

		var diferencaEmHoras = Duration.between(horarioAgora,  consulta.getData()).toHours();

		if (diferencaEmHoras < 24) {

			throw new ValidacaoException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");

		}


	}

}
