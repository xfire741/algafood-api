package br.com.eduardo.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import br.com.eduardo.algafood.api.v1.assembler.PedidoInputDisassembler;
import br.com.eduardo.algafood.api.v1.assembler.PedidoModelAssembler;
import br.com.eduardo.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import br.com.eduardo.algafood.api.v1.model.PedidoDTO;
import br.com.eduardo.algafood.api.v1.model.PedidoResumoDTO;
import br.com.eduardo.algafood.api.v1.model.input.PedidoInputDTO;
import br.com.eduardo.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.com.eduardo.algafood.core.data.PageWrapper;
import br.com.eduardo.algafood.core.data.PageableTranslator;
import br.com.eduardo.algafood.core.security.AlgaSecurity;
import br.com.eduardo.algafood.core.security.CheckSecurity;
import br.com.eduardo.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardo.algafood.domain.exception.NegocioException;
import br.com.eduardo.algafood.domain.filter.PedidoFilter;
import br.com.eduardo.algafood.domain.model.Pedido;
import br.com.eduardo.algafood.domain.model.Usuario;
import br.com.eduardo.algafood.domain.repository.PedidoRepository;
import br.com.eduardo.algafood.domain.service.EmissaoPedidoService;
import br.com.eduardo.algafood.infraestructure.repository.spec.PedidoSpecs;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

	@Autowired
	private PedidoModelAssembler assembler;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService cadastroPedidoService;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping(path = "/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		return assembler.toModel(cadastroPedidoService.buscarOuFalhar(codigoPedido));
	}

	@Override
	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro,
			@PageableDefault(size = 10) Pageable pageable) {
		Pageable pageableTraduzido = traduzirPageable(pageable);

		Page<Pedido> pedidosPage = pedidoRepository.findAll(
				PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

		pedidosPage = new PageWrapper<>(pedidosPage, pageable);

		return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}

	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

			novoPedido = cadastroPedidoService.emitir(novoPedido);

			return assembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	private Pageable traduzirPageable(Pageable apiPeageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"nomeRestaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal");
		return PageableTranslator.translate(apiPeageable, mapeamento);
	}

}
