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

import br.com.eduardo.algafood.api.assembler.CidadeInputDisassembler;
import br.com.eduardo.algafood.api.assembler.CidadeModelAssembler;
import br.com.eduardo.algafood.api.model.CidadeDTO;
import br.com.eduardo.algafood.api.model.input.CidadeInputDTO;
import br.com.eduardo.algafood.api.openapi.controller.CidadeControllerOpenApi;
import br.com.eduardo.algafood.domain.exception.EstadoNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.repository.CidadeRepository;
import br.com.eduardo.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionDTO(cidadeRepository.findAll());
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTO buscar(@PathVariable Long id) {
		return cidadeModelAssembler.toDTO(cadastroCidade.buscarOuFalhar(id));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
			return cidadeModelAssembler.toDTO(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		cadastroCidade.excluir(id);
	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTO atualizar(@PathVariable Long id, 
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

			cidadeInputDisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

			return cidadeModelAssembler.toDTO(cadastroCidade.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
