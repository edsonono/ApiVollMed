package med.voll.api.controller;


import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.MotivoCancelamento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@GetMapping
	public String olaMundo() {
		return "Hello World Spring!";
	}
	

}
