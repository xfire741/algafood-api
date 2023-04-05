package br.com.eduardo.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.eduardo.algafood.api.model.EnderecoDTO;
import br.com.eduardo.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
				Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoDTOTypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
		
	}
	
}
