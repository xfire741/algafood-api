package br.com.eduardo.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator  implements ConstraintValidator<FileContentType, MultipartFile>{
	
	private List<String> allowedContenteTypes;
	
	@Override
	public void initialize(FileContentType constraint) {
		this.allowedContenteTypes = Arrays.asList(constraint.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		
		return value == null || this.allowedContenteTypes.contains(value.getContentType());
		
	}
	
	

}
