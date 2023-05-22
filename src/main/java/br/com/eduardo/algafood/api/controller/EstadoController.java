package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.eduardo.algafood.api.assembler.EstadoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.EstadoModelAssembler;
import br.com.eduardo.algafood.api.model.EstadoDTO;
import br.com.eduardo.algafood.api.model.input.EstadoInputDTO;
import br.com.eduardo.algafood.api.openapi.controller.EstadoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Estado;
import br.com.eduardo.algafood.domain.repository.EstadoRepository;
import br.com.eduardo.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@Autowired
	private EstadoModelAssembler estadoModelAssembler;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EstadoDTO> listar(){
		return estadoModelAssembler.toCollectionDTO(estadoRepository.findAll());
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoModelAssembler.toDTO(cadastroEstado.buscarOuFalhar(id));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInputDTO);
		
		return cadastroEstado.salvar(estado);
	}

	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
			cadastroEstado.excluir(id);
	}
	
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);

		estadoInputDisassembler.copyToDomainObject(estadoInputDTO, estadoAtual);
		
		return estadoModelAssembler.toDTO(cadastroEstado.salvar(estadoAtual));
		
	}
	
	
}
