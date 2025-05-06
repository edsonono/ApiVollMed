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
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key") //permite cabeçalho de autorização no Swagger
public class MedicoController {

	@Autowired
	public MedicoRepository repository;

	@PostMapping
	@Transactional
	//public void cadastrar(@RequestBody String json) {
	//public void cadastrar(@RequestBody DadosCadastroMedico dados) {
	//public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {   //Indica que DadosCadastroMedico possui anotações de validação.
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
			                                             UriComponentsBuilder uriBuilder) {   //Alterações para retornar um ResponseEntity e Uri

		//System.out.println(dados);
		//repository.save(new Medico(dados));

		var medico = new Medico(dados);
		repository.save(medico);

		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();  //concatena a uri com /medicos/ buildAndExpand(medico.getId()) e transforma em uri.

		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));  //corpo é o DTO do Medico
	}

	@GetMapping                //para leitura o método deverá ser Get e não precisa de @Transactional pois não altera a base.
	//public List<Medico> listar(){
	//public List<DadosListagemMedico> listar(){                   //Retorna uma lista de DTO para não trazer todos os atributos
	//public Page<DadosListagemMedico> listar(Pageable paginacao){   //incluido a interface Pageable do Spring para trazer paginação e retorna a classe Page ao inves de List
	//public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){   //incluido o padrão @PageableDefault(size = 10, sort = {"nome"})
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){   //Alteração para retornar um ResponseEntity e não mais um DTO diretamente.

		//.stream().map(DadosLsitagemMedico::new) --> recurso do java 8 que converte Medico para DadosListagemMedico
		//return repository.findAll().stream().map(DadosListagemMedico::new).toList();
		//return repository.findAll(paginacao).stream().map(DadosListagemMedico::new).toList();  //incluido o pageable como parametro
		//return repository.findAll(paginacao).map(DadosListagemMedico::new);  //E como o retorno é um Page, não é mais necessario o .stream() e nem o .toList().

		//var page = repository.findAll(paginacao).map(DadosListagemMedico::new); //Alteração para retornar um ResponseEntity e não mais um DTO diretamente.
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Retornar apenas os médicos ativo
		return ResponseEntity.ok(page);                                         //Alteração para retornar um ResponseEntity e não mais um DTO diretamente.

	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());

		medico.atualizarInformacoes(dados);

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {

		//	repository.deleteById(id);                     //exclusão física

		var medico = repository.getReferenceById(id);      //exclusão lógica, carrega o id da tabela.
		medico.excluir();                                  //exclusão lógica, atualiza atributo ativo para false.

		return ResponseEntity.noContent().build();         //Retornar um ResponseEntity de código 204 - No Content.

	}

	@GetMapping("/{id}")
	public ResponseEntity exibeDetalhes(@PathVariable Long id) {

		var medico = repository.getReferenceById(id);      //carrega o id da tabela.

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));         //Retornar um ResponseEntity de código 200 - ok + corpo

	}

}
