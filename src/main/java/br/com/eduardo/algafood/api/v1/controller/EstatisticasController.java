package br.com.eduardo.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.algafood.api.v1.AlgaLinks;
import br.com.eduardo.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import br.com.eduardo.algafood.domain.filter.VendaDiariaFilter;
import br.com.eduardo.algafood.domain.model.dto.VendaDiaria;
import br.com.eduardo.algafood.domain.service.VendaReportService;
import br.com.eduardo.algafood.infraestructure.service.query.VendaQueryServiceImpl;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private VendaReportService vendaReportService;

	@Autowired
	private VendaQueryServiceImpl vendaQueryService;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasModel estatisticas() {
	    var estatisticasModel = new EstatisticasModel();
	    
	    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	}  
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
	
}
