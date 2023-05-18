package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.eduardo.algafood.domain.exception.EstadoNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.repository.CidadeRepository;
import br.com.eduardo.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionDTO(cidadeRepository.findAll());
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{id}")
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") @PathVariable Long id) {
		return cidadeModelAssembler.toDTO(cadastroCidade.buscarOuFalhar(id));
	}

	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
			return cidadeModelAssembler.toDTO(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade por ID")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@ApiParam(value = "ID de uma cidade") @PathVariable Long id) {
		cadastroCidade.excluir(id);
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") 
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
