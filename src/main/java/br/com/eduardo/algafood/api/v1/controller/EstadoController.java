package br.com.eduardo.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.assembler.EstadoInputDisassembler;
import br.com.eduardo.algafood.api.v1.assembler.EstadoModelAssembler;
import br.com.eduardo.algafood.api.v1.model.EstadoDTO;
import br.com.eduardo.algafood.api.v1.model.input.EstadoInputDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import br.com.eduardo.algafood.core.security.CheckSecurity;
import br.com.eduardo.algafood.domain.model.Estado;
import br.com.eduardo.algafood.domain.repository.EstadoRepository;
import br.com.eduardo.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@CheckSecurity.Estados.PodeConsultar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<EstadoDTO> listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@CheckSecurity.Estados.PodeConsultar
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoModelAssembler.toModel(cadastroEstado.buscarOuFalhar(id));
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInputDTO);
		
		return cadastroEstado.salvar(estado);
	}

	@CheckSecurity.Estados.PodeEditar
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
			cadastroEstado.excluir(id);
	}
	
	@CheckSecurity.Estados.PodeEditar
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);

		estadoInputDisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
		
		return estadoModelAssembler.toModel(cadastroEstado.salvar(estadoAtual));
		
	}
	
	
}
