package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;


@Component("ValidadorHorarioAntecedenciaAgendamento")  //se 2 classes com o mesmo nome em pacotes diferentes, diferenciar pelo @Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

	@Override
	public void validar(DadosAgendamentoConsulta dados) {

	    var dataConsulta = dados.data();

	    var agora = LocalDateTime.now();

	    var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();   //em minutos

	    if ( diferencaEmMinutos < 30){

	    	throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos");

	    }

	}
}
