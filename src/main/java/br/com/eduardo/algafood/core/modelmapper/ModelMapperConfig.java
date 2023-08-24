package br.com.eduardo.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.eduardo.algafood.api.v1.model.EnderecoDTO;
import br.com.eduardo.algafood.api.v1.model.input.ItemPedidoInputDTO;
import br.com.eduardo.algafood.api.v2.model.input.CidadeInputDTOV2;
import br.com.eduardo.algafood.domain.model.Cidade;
import br.com.eduardo.algafood.domain.model.Endereco;
import br.com.eduardo.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(CidadeInputDTOV2.class, Cidade.class)
	    .addMappings(mapper -> mapper.skip(Cidade::setId));
		
		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoDTOTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value));
		
		modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		return modelMapper;
		
	}
	
}
