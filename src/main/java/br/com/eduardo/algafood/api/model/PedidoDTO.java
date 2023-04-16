package br.com.eduardo.algafood.api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoDTO {

	private String codigo;
	private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private EnderecoDTO enderecoEntrega;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataEntrega;
    private FormaPagamentoDTO formaPagamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private List<ItemPedidoDTO> itens = new ArrayList<>();
	
}
