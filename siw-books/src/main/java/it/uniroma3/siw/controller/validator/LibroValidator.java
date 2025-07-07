package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;

@Component
public class LibroValidator implements Validator{

	@Autowired
	private LibroRepository libroRepository;
	
	@Override
	public void validate(Object o, Errors errors) {
		Libro libro = (Libro) o; 
		if(libro.getTitolo() != null && libro.getAnnoPubblicazione() != null
				&& libroRepository.existsByTitoloAndAnnoPubblicazione(libro.getTitolo(),libro.getAnnoPubblicazione())) {
			errors.reject("libro.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Libro.class.equals(aClass);
	}
}
