package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.LibroValidator;
import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.AutoreRepository;
import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class LibroController {
	
	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private LibroValidator libroValidator;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private RecensioneService recensioneService;
	
	// Aggiunge un nuovo libro
	@GetMapping(value="/admin/formNewLibro")
	public String formNewLibro(Model model) { //model è un parametro passato da Spring per passare dati dal controller alla vista
		model.addAttribute("libro", new Libro()); //aggiunge un oggetto 'libro' al model
		return "admin/formNewLibro.html"; //il valore di ritorno che verrà mostrato quando viene soddisfata la richiesta
	}
	
	//Modifica un libro
	@GetMapping(value="/admin/formUpdateLibro/{id}")
	public String formUpdateLibro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("libro", libroService.getLibroById(id));
		return "admin/formUpdateLibro.html";
	}
	
	@GetMapping("/admin/updateAutori/{id}")
	public String updateAutori(@PathVariable("id") Long id, Model model) {
		List<Autore> autoriToAdd = this.autoriToAdd(id);
		model.addAttribute("autoriToAdd", autoriToAdd);
		model.addAttribute("libro", this.libroService.getLibroById(id));
		return "admin/autoriToAdd.html";
	}
	
	private List<Autore> autoriToAdd(Long libroId){
		List<Autore> autoriToAdd = new ArrayList<>();
		
		for(Autore a : autoreRepository.findAutoriNotInLibro(libroId)) {
			autoriToAdd.add(a);
		}
		return autoriToAdd;
	}
	
	@GetMapping(value="/admin/indexLibro")
	public String indexLibro() {
		return "admin/indexLibro.html";
	}
	
	@GetMapping(value="/admin/manageLibri")
	public String manageLibri(Model model) {
		model.addAttribute("libri", this.libroService.getTuttiLibri());
		return "admin/manageLibri.html";
	}
	
	@GetMapping("/formSearchLibri")
	public String formSearchLibri() {
		return "formSearchLibri.html";
	}
	
	@GetMapping("/libro")
	public String getLibri(Model model) {
		model.addAttribute("libri", this.libroService.getTuttiLibri());
		return "libri.html";
	}
	
	@PostMapping(value = "/libro")
	public String newMovie(@Valid @ModelAttribute("libro") Libro libro, 
			BindingResult bindingResult, Model model) {
		this.libroValidator.validate(libro, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.libroService.createNewLibro(libro);
			model.addAttribute("libro", libro);
			return "libro.html";
		} else {
			return "admin/formNewLibro.html";
		}
	}
	
	@PostMapping(value = "/searchLibri")
	public String searchLibri(Model model, @RequestParam(required = false) Integer annoPubblicazione) {
		if(annoPubblicazione != null) {	
			List<Libro> libroAnnoPubblicazione = this.libroRepository.findByAnnoPubblicazione(annoPubblicazione);
			model.addAttribute("libri", libroAnnoPubblicazione);
		}else {
			model.addAttribute("libri", new ArrayList<>());
		}
		return "foundLibri.html";
	}
	
	@PostMapping(value="/admin/updateLibro/{libroId}")
	public String updateLibro(@ModelAttribute("libro") Libro newLibro, @PathVariable("libroId") Long libroId, Model model) {
		Libro oldLibro = this.libroService.getLibroById(libroId);
		Libro libro = this.libroService.updateLibro(oldLibro, newLibro);
		this.libroService.saveLibro(libro);
		model.addAttribute("libro", libro);
		return "admin/formUpdateLibro.html";
	}
	
	@GetMapping("/libro/{id}")
	public String getLibro(@PathVariable("id") Long id, Model model) {
		Libro libro = this.libroService.getLibroById(id);
		model.addAttribute("libro", libro);
		return "libro.html";
	}
	
	@GetMapping(value = "/admin/addAutoreToLibro/{autoreId}/{libroId}")
	public String addAutoreToLibro(@PathVariable("autoreId") Long autoreId, @PathVariable("libroId") Long libroId, Model model) {
		Libro libro = this.libroService.addAutoreToLibro(libroId, autoreId);
		List<Autore> autoreToAdd = this.libroService.findAutoriNotLibro(libroId);
		
		model.addAttribute("libro", libro);
		model.addAttribute("autoriToAdd", autoreToAdd);
		
		return "admin/autoriToAdd.html";
	}
	
	@GetMapping(value="/admin/removeAutoreFromLibro/{autoreId}/{libroId}")
	public String removeAutoreFromLibro(@PathVariable("autoreId") Long autoreId, @PathVariable("libroId") Long libroId, Model model) {
		Libro libro = this.libroService.removeAutoreFromLibro(libroId, autoreId);
		List<Autore> autoriToAdd = this.libroService.findAutoriNotLibro(libroId);
		
		model.addAttribute("libro", libro);
		model.addAttribute("autoriToAdd", autoriToAdd);
		
		return "admin/autoriToAdd.html";
	}
	
	@GetMapping(value = "/user/libriUser")
	public String getUserLibri(Model model) {
		//SecurityContextHolder per ottenere l'oggetto che rappresenta l'utente attualmente loggato
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String email = null;

	    if (principal instanceof OidcUser) {
	        email = ((OidcUser) principal).getEmail(); // login con Google
	    } else if (principal instanceof UserDetails) {
	        email = ((UserDetails) principal).getUsername(); // login classico
	    } else {
	        throw new IllegalStateException("Tipo di autenticazione non riconosciuto: " + principal.getClass());
	    }

	    Credentials credentials = credentialsService.getCredentials(email); //recupera le credenziali dal DB
	    if (credentials == null) {
	        return "redirect:/registerGoogle"; // reindirizza alla pagina di registrazione
	    }

	    model.addAttribute("user", credentials.getUser());
	    model.addAttribute("libri", this.libroRepository.findAll());
		return "user/libriUser.html";
	}
	
	@GetMapping("user/indexUser")
	public String indexUser(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String email = null;

	    if (principal instanceof OidcUser) {
	        email = ((OidcUser) principal).getEmail(); // login con Google
	    } else if (principal instanceof UserDetails) {
	        email = ((UserDetails) principal).getUsername(); // login classico
	    } else {
	        throw new IllegalStateException("Tipo di utente non supportato: " + principal.getClass());
	    }

	    Credentials credentials = credentialsService.getCredentials(email);
	    if (credentials == null) {
	        return "redirect:/registerGoogle"; // se utente Google non è ancora nel DB
	    }

	    model.addAttribute("user", credentials.getUser());
	    model.addAttribute("username", credentials.getUsername());
	    return "user/indexUser.html";
	}
	
	@GetMapping("/user/libroUser/{id}")
	public String getUserLibro(@PathVariable("id") Long id, Model model) {
		Libro libro = this.libroService.getLibroById(id);
		model.addAttribute("libro", libro);
		return "user/libroUser.html";
	}
	
	@GetMapping(value="/admin/recensioniInLibro/{libroId}")
	public String addRecensioneToLibro(@PathVariable("libroId") Long libroId, Model model) {
		Libro libro = this.libroService.getLibroById(libroId);
		model.addAttribute("libro", libro);
		model.addAttribute("recensione", new Recensione());
		return "admin/newRecensione.html";
	}
	
	@GetMapping(value="/admin/deleteRecensione/{recensioneId}/{libroId}")
	public String deleteRecensione(@PathVariable("recensioneId") Long recensioneId, @PathVariable("libroId") Long libroId, Model model){
		this.recensioneService.deleteRecensione(recensioneId);
		Libro libro = this.libroService.getLibroById(libroId);
		model.addAttribute("libro", libro);
		model.addAttribute("recensione", libro.getRecensioni());
		return "admin/formUpdateLibro.html";
	}
	
	@PostMapping(value="/admin/deleteLibro/{libroId}")
	public String deleteLibro(@PathVariable("libroId") Long libroId) {
		this.libroService.deleteById(libroId);
		return "redirect:/libro";
	}

}
