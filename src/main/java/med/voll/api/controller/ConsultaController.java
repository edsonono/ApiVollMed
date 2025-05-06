package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;


@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key") //permite cabeçalho de autorização no Swagger
public class ConsultaController {


    @Autowired
    private AgendaDeConsultas agenda;


	@PostMapping
	@Transactional
	public ResponseEntity agendar(@RequestBody @Valid  DadosAgendamentoConsulta dados) {

		//System.out.println(dados);

		//agenda.agendar(dados);  //classe com as regras de negócio
		var dto = agenda.agendar(dados);

		//return ResponseEntity.ok(new DadosDetalhamentoConsulta(null,null,null,null));
		return ResponseEntity.ok(dto);
	}


	@DeleteMapping
	@Transactional
	public ResponseEntity cancelar(@RequestBody @Valid  DadosCancelamentoConsulta dados) {

		agenda.cancelar(dados);  //classe com as regras de negócio que fará o cancelamento

		return ResponseEntity.noContent().build();         //Retornar um ResponseEntity de código 204 - No Content.

	}


}
