package it.uniroma3.siw.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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

	@Transactional  //garantisce che tutte le operazioni nel metodo vengano eseguite come un'unica transazione
	public void deteteRecensione(Long recensioneId) {
		Recensione recensione = this.getRecensioneById(recensioneId);
		Libro libro = recensione.getLibro();
		recensione.getRecensore().getRecensioni().remove(recensione);
		recensione.getLibro().getRecensioni().remove(recensione);
		this.libroRepository.save(libro);
		this.recensioneRepository.delete(recensione);
	}
	
	@Transactional 
	public Recensione getRecensioneById(Long id) {
		return this.rencesioneRepository.findById(id).get();
	}
	
	@Transactional
	public Recensione newRecensione(@Valid Recensione recensione, Long libroId) {
		Libro libro = this.libroRepository.findById(libroId).get();
//		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String usernameOrEmail;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		    usernameOrEmail = ((UserDetails) principal).getUsername();
		} else if (principal instanceof OidcUser) {
		    usernameOrEmail = ((OidcUser) principal).getEmail(); // oppure getAttribute("email")
		} else {
		    throw new IllegalStateException("Tipo utente non supportato");
		}
		Credentials credentials = credentialsService.getCredentials(usernameOrEmail);
		recensione.setLibro(libro);
		recensione.setRecensore(credentials.getUser());
		this.recensioneRepository.save(recensione);
		return recensione;
	}
}
