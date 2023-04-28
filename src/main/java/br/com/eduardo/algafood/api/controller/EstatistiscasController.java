package br.com.eduardo.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.domain.filter.VendaDiariaFilter;
import br.com.eduardo.algafood.domain.model.dto.VendaDiaria;
import br.com.eduardo.algafood.infraestructure.service.VendaQueryServiceImpl;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatistiscasController {

	@Autowired
	private VendaQueryServiceImpl vendaQueryService;
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		return vendaQueryService.consultarVendasDiarias(filtro);
	}
	
}
