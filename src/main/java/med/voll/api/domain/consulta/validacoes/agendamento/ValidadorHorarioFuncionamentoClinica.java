package med.voll.api.domain.consulta.validacoes.agendamento;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    @Override
	public void validar(DadosAgendamentoConsulta dados) {

        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);  //se cair no domingo retorna true.

        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;           //verifica se horario é antes das 7

        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;     //verifica se horario é após as 18


        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {

        	throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");

        }

    }

}
