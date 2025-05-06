package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;  //possui todos os metodos necessarios para manipular a tabela
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public interface MedicoRepository extends JpaRepository <Medico, Long> {


	Page<Medico> findAllByAtivoTrue(Pageable paginacao);    //este nome segue uma padronização fazendo que o Spring entenda que deve retornar ativo = 1.


	@Query("""
            select m from Medico m
            where
            m.ativo = true
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                    select c.medico.id from Consulta c
                    where c.data = :data
                      and c.motivoCancelamento is null
            )
            order by rand()
            limit 1
            """)
	Medico escolheMedicoAleatorioLivreNaData(Especialidade especialidade, @NotNull @Future LocalDateTime data);
	//chamado por AgendaDeConsultas

	@Query("""
            select m.ativo from Medico m
            where
            m.id = :idMedico
		""")
	Boolean findAtivoById(Long idMedico);  //busca a situação do médico  //cuidado: não esquecer dos : no parametro da query
	//chamado por ValidadorMedicoAtivo


}
