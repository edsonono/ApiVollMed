package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.NotNull;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	Page<Paciente> findAllByAtivoTrue(Pageable paginacao);  //só esta declaração op Spring já entende que deve selecionar apenas os ativos.

	@Query("""
			select p.ativo from Paciente p
			where p.id = :idPaciente
			""")
	Boolean findAtivoById(@NotNull Long idPaciente);   //cuidado: não esquecer dos : no parametro da query
	//chamado por ValidadorPacienteAtivo

}



