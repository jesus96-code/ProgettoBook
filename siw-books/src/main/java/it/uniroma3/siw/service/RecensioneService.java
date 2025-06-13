package it.uniroma3.siw.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {
	
	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private RecensioneRepository rencesioneRepository;
	
	@Autowired
	private RecensioneService  recensioneService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private RecensioneRepository recensioneRepository;

	@Transactional
	public void deteteRecensione(Long recensioneId) {
		Recensione recensione = this.getRecensioneById(recensioneId);
		Libro libro = recensione.getLibro();
		recensione.getRecensore().getRecensioni().remove(recensione);
		recensione.getLibro().getRecensioni().remove(recensione);
		this.libroRepository.save(libro);
		this.rencesioneRepository.delete(recensione);
	}
	
	@Transactional 
	public Recensione getRecensioneById(Long id) {
		return this.rencesioneRepository.findById(id).get();
	}
	
	@Transactional
	public Recensione newRecensione(@Valid Recensione recensione, Long libroId) {
		Libro libro = this.libroRepository.findById(libroId).get();
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(user.getUsername());
		recensione.setLibro(libro);
		recensione.setRecensore(credentials.getUser());
		this.recensioneRepository.save(recensione);
		return recensione;
	}
}
