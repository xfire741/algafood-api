package br.com.eduardo.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.AlgaLinks;

@RestController
@RequestMapping
public class RootEntryPointController {

	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(algaLinks.linkToEstados("estados"));
		rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}
