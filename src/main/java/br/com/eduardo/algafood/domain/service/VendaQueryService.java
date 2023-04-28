package br.com.eduardo.algafood.domain.service;

import java.util.List;

import br.com.eduardo.algafood.domain.filter.VendaDiariaFilter;
import br.com.eduardo.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
	
}
