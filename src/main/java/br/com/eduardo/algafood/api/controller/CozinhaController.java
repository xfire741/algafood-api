package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.eduardo.algafood.api.assembler.CozinhaInputDisassembler;
import br.com.eduardo.algafood.api.assembler.CozinhaModelAssembler;
import br.com.eduardo.algafood.api.model.CozinhaDTO;
import br.com.eduardo.algafood.api.model.input.CozinhaInputDTO;
import br.com.eduardo.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.repository.CozinhaRepository;
import br.com.eduardo.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CozinhaDTO> listar(@PageableDefault(size = 2) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		List<CozinhaDTO> cozinhasDTO = cozinhaModelAssembler.toCollectionDTO(cozinhasPage.getContent());
		
		Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<>(cozinhasDTO, pageable, 
				cozinhasPage.getTotalElements());
		
		return cozinhasDTOPage;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTO buscar(@PathVariable("id") Long id) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(id);
		
		return cozinhaModelAssembler.toDTO(cozinha);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInputDTO);
		
		return cozinhaModelAssembler.toDTO(cadastroCozinha.salvar(cozinha));
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInputDTO, cozinhaAtual);
		
		return 	cozinhaModelAssembler.toDTO(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
			cadastroCozinha.excluir(id);
	}

}
