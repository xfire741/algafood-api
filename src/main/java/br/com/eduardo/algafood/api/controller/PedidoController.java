package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.PedidoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.PedidoModelAssembler;
import br.com.eduardo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.eduardo.algafood.api.model.PedidoDTO;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.api.model.input.PedidoInputDTO;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.PedidoRepository;
import br.com.eduardo.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PedidoResumoModelAssembler resumoModelAssembler;
	
	@Autowired
	private PedidoModelAssembler assembler;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService cadastroPedidoService;
	
	@GetMapping("/{id}")
	public PedidoDTO buscar(@PathVariable Long id) {
		return assembler.toDTO(cadastroPedidoService.buscarOuFalhar(id));
	}
	
	@GetMapping
	public List<PedidoResumoDTO> listar() {
		return resumoModelAssembler.toCollectionDTO(pedidoRepository.findAll());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = cadastroPedidoService.emitir(novoPedido);

	        return assembler.toDTO(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
}
