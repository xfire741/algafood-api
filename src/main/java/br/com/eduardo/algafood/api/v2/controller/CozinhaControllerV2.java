package br.com.eduardo.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import br.com.eduardo.algafood.api.v2.assembler.CozinhaInputDisassemblerV2;
import br.com.eduardo.algafood.api.v2.assembler.CozinhaModelAssemblerV2;
import br.com.eduardo.algafood.api.v2.model.CozinhaDTOV2;
import br.com.eduardo.algafood.api.v2.model.input.CozinhaInputDTOV2;
import br.com.eduardo.algafood.api.v2.openapi.CozinhaControllerV2OpenApi;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.repository.CozinhaRepository;
import br.com.eduardo.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/v2/cozinhas")
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

	@Autowired
	private CozinhaInputDisassemblerV2 cozinhaInputDisassembler;
	
	@Autowired
	private CozinhaModelAssemblerV2 cozinhaModelAssembler;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<CozinhaDTOV2> listar(@PageableDefault(size = 2) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDTOV2> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return cozinhasPagedModel; 
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTOV2 buscar(@PathVariable("id") Long id) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(id);
		
		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaInputDTOV2 cozinhaInputDTO) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
		
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTOV2 atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTOV2 cozinhaInputDTO) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		
		return 	cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
			cadastroCozinha.excluir(id);
	}

}
