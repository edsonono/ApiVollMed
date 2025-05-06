package med.voll.api.domain.paciente;

public record DadosListagemPacientes(Long id, String nome, String email, String cpf) {   //contem os parametros que quero devover

	 public DadosListagemPacientes(Paciente paciente) {                         //seto os parametros do record com os dados de Paciente, quando o record for instanciado.

		this(paciente.getId(), paciente.getNome(),paciente.getEmail(),paciente.getCpf());

	}

}
