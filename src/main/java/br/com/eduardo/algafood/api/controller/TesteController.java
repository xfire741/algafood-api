package br.com.eduardo.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.domain.model.Cozinha;
import br.com.eduardo.algafood.domain.model.Restaurante;
import br.com.eduardo.algafood.domain.repository.CozinhaRepository;
import br.com.eduardo.algafood.domain.repository.RestauranteRepository;


@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/primeiro")
	public Optional<Cozinha> cozinhaPrimeiro() {
		return cozinhaRepository.buscarPrimeiro();
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome-id")
	public List<Restaurante> restaurantesPorNomeEId(String nome, Long id) {
		return restauranteRepository.consultarPorNome(nome, id);
	}
	
	@GetMapping("/restaurantes/por-primeiro-nome")
	public Optional<Restaurante> restaurantePorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/por-nome-frete")
	public List<Restaurante> restaurantePorNomeTaxaFrete(String nome, 
			BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
		
	}
	
	@GetMapping("/restaurantes/por-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome, 
			BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		return restauranteRepository.findComFreteGratis(nome);
		
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}

	
}
