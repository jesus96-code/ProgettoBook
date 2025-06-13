package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.repository.AutoreRepository;

@Service
public class AutoreService {
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	@Transactional
	public List<Autore> findAutoriNotInLibro(Long libroId){
		List<Autore> autoriToAdd = new ArrayList<>();
		
		for(Autore a : autoreRepository.findAutoriNotInLibro(libroId)) {
			autoriToAdd.add(a);
		}
		return autoriToAdd;
 	}
	
	@Transactional
	public Autore getAutoreById(Long autoreId) {
		return autoreRepository.findById(autoreId).get();
	}

}
