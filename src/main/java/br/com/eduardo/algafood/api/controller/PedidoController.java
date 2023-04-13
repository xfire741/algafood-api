package br.com.eduardo.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.assembler.PedidoModelAssembler;
import br.com.eduardo.algafood.api.assembler.PedidoResumoModelAssembler;
import br.com.eduardo.algafood.api.model.PedidoDTO;
import br.com.eduardo.algafood.api.model.PedidoResumoDTO;
import br.com.eduardo.algafood.domain.repository.PedidoRepository;
import br.com.eduardo.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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
	
}
