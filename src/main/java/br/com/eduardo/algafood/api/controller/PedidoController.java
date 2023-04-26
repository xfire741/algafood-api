package br.com.eduardo.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import br.com.eduardo.algafood.api.assembler.PedidoInputDisassembler;
import br.com.eduardo.algafood.api.assembler.PedidoModelAssembler;
import br.com.eduardo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.eduardo.algafood.api.model.PedidoDTO;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.api.model.input.PedidoInputDTO;
import br.com.eduardo.algafood.core.data.PageableTranslator;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.PedidoRepository;
import br.com.eduardo.algafood.domain.repository.filter.PedidoFilter;
import br.com.eduardo.algafood.domain.service.EmissaoPedidoService;
import br.com.eduardo.algafood.infraestructure.repository.spec.PedidoSpecs;

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
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		return assembler.toDTO(cadastroPedidoService.buscarOuFalhar(codigoPedido));
	}
		
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecs.usandoFiltro(filtro), pageable);
	    
	    List<PedidoResumoDTO> pedidosResumoModel = resumoModelAssembler
	            .toCollectionDTO(pedidosPage.getContent());
	    
	    Page<PedidoResumoDTO> pedidosResumoModelPage = new PageImpl<>(
	            pedidosResumoModel, pageable, pedidosPage.getTotalElements());
	    
	    return pedidosResumoModelPage;
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
	
	private Pageable traduzirPageable(Pageable apiPeageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"nomeRestaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		return PageableTranslator.translate(apiPeageable, mapeamento);
	}
	
}
