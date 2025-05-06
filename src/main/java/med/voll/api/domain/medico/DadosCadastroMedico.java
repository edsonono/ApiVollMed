package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
		@NotBlank(message = "O nome é obrigatório")               //Verifica se não está nulo e nem vazio(com espaco em branco apenas).
		String nome,

		@NotBlank(message = "O e-mail é obrigatório")             //incluído message para personalização das mensagens.
		@Email(message = "Formato do e-mail inválido")
		String email,

		@NotBlank(message = "O telefone é obrigatório")
		String telefone,                   //incluído novo campo

		@NotBlank(message = "O crm é obrigatório")
		@Pattern(regexp = "\\d{4,6}" , message = "Formato do crm inválido")     // \\d - indica que tem dígitos e {4,6} indica o tamanho de 4 a 6 dígitos.
		String crm,

        @NotNull(message = "A especialidade é obrigatória")                           //Como não é uma String não pode ser @NotBlank
		Especialidade especialidade,

		@NotNull(message = "O endereco é obrigatório")
		@Valid DadosEndereco endereco) {          //indica que o DadosEndereco também possui anotações a serem validadas

}
