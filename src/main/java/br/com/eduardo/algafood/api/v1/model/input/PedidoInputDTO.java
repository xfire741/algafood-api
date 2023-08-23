package br.com.eduardo.algafood.api.v1.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoInputDTO {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;
    
    @Valid
    @NotNull
    private EnderecoInputDTO enderecoEntrega;
    
    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;
    
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInputDTO> itens;
    
} 
