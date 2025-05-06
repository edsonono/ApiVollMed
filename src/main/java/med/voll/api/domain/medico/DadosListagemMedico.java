package med.voll.api.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

	public DadosListagemMedico(Medico medico) {              //construtor que recebe médico e seta os 5 atributos do DTO
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
	}

}
