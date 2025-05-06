package med.voll.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosDetalhamentoPaciente;
import med.voll.api.domain.paciente.DadosListagemPacientes;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;


@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key") //permite cabeçalho de autorização no Swagger
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente dados,
    		UriComponentsBuilder uriBuilder) {    //criado mais 1 parametro UriComponentsBuilder

    	//repository.save(new Paciente(dados));

    	Paciente paciente = new Paciente(dados);
		repository.save(paciente);

    	var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

    	return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    //retorna um List
    //public List<DadosListagemPacientes> listar(){
    //	return repository.findAll().stream().map(DadosListagemPacientes::new).toList();
    //}

    //retorna um Page
    //public Page<DadosListagemPacientes> listar(Pageable paginacao){
    //	return repository.findAll(paginacao).map(DadosListagemPacientes::new);
    //}

    //retorna ResponseEntity do Page
    //public ResponseEntity<Page<DadosListagemPacientes>> listar(Pageable paginacao){
    //	var pacientes =  repository.findAll(paginacao).map(DadosListagemPacientes::new);
    //	return  ResponseEntity.ok(pacientes);
    //}

    //incluido ordenação padrão: @PageableDefault(size = 10, sort = {"nome"})
    public ResponseEntity<Page<DadosListagemPacientes>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
    	//var pacientes =  repository.findAll(paginacao).map(DadosListagemPacientes::new);
    	var page =  repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
    	return  ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizacaoPaciente dados) {

    	var paciente = repository.getReferenceById(dados.id());    //getReferenceById --retorna uma referencia de paciente

    	paciente.AtualizarInformacoes(dados);

      	return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {  //lembrar @PathVariable para pegar a variavel da do caminho da url

    	//repository.deleteById(id);                //exclusão física
    	var paciente = repository.getReferenceById(id);
    	paciente.excluir();

    	return ResponseEntity.noContent().build();  //Retornar um ResponseEntity de código 204 - No Content.
    }


    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {  //lembrar @PathVariable para pegar a variavel da do caminho da url

    	var paciente = repository.getReferenceById(id);

    	return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));  //Retornar um ResponseEntity de código 200-ok
    }


}


