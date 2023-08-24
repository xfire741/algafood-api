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

import br.com.eduardo.algafood.api.v1.assembler.GrupoInputDisassembler;
import br.com.eduardo.algafood.api.v1.assembler.GrupoModelAssembler;
import br.com.eduardo.algafood.api.v1.model.GrupoDTO;
import br.com.eduardo.algafood.api.v1.model.input.GrupoInputDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Grupo;
import br.com.eduardo.algafood.domain.repository.GrupoRepository;
import br.com.eduardo.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDTO buscar(@PathVariable Long id) {
		return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(id));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<GrupoDTO> listar() {
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInputDTO);
		
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
	}
	
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(id);
		
		grupoInputDisassembler.copyToDomainObject(grupoInputDTO, grupo);
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		cadastroGrupoService.excluir(id);
	}
	
	
	
}
