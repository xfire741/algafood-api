package br.com.eduardo.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.ResourceUriHelper;
import br.com.eduardo.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import br.com.eduardo.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import br.com.eduardo.algafood.api.v2.model.CidadeDTOV2;
import br.com.eduardo.algafood.api.v2.model.input.CidadeInputDTOV2;
import br.com.eduardo.algafood.core.web.AlgaMediaTypes;
import br.com.eduardo.algafood.domain.exception.EstadoNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.repository.CidadeRepository;
import br.com.eduardo.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeControllerV2 {

	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;

	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeDTOV2> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll()); 
		
	}

	@GetMapping(path = "/{id}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CidadeDTOV2 buscar(@PathVariable Long id) {
		return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(id));
	}

	@PostMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputDTO);
			
			
			CidadeDTOV2 cidadeDTO = cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getIdCidade());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

//	Não pode ser mapeado na mesma URL em um MediaType diferente, já que não aceita entrada e retorna void.
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	@DeleteMapping("/{id}")
//	public void remover(@PathVariable Long id) {
//		cadastroCidade.excluir(id);
//	}

	@PutMapping(path = "/{id}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CidadeDTOV2 atualizar(@PathVariable Long id, 
			@RequestBody @Valid CidadeInputDTOV2 cidadeInputDTO) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

			cidadeInputDisassembler.copyToDomainObject(cidadeInputDTO, cidadeAtual);

			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
