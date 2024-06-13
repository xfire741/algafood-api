package br.com.eduardo.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.eduardo.algafood.core.storage.StorageProperties.TipoStorage;
import br.com.eduardo.algafood.domain.service.FotoStorageService;
import br.com.eduardo.algafood.infraestructure.service.storage.LocalFotoStorageService;
import br.com.eduardo.algafood.infraestructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@Profile("prod")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
	}
	
	@Bean
	public FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {
			return new S3FotoStorageService();
		} else {
			return new LocalFotoStorageService();
		}
	}
	
}
