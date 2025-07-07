package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.AutoreRepository;
import it.uniroma3.siw.service.AutoreService;

@Controller
public class AutoreController {
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	@Autowired
	private AutoreService autoreService;
	
	@GetMapping(value = "/admin/indexAutore")
	public String indexAutore() {
		return "admin/indexAutore.html";
	}
	
	@GetMapping(value="/admin/formNewAutore")
	public String formNewAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "admin/formNewAutore.html";
	}
	
	@GetMapping(value="/admin/manageAutori")
	public String manageAutori(Model model) {
		model.addAttribute("autori", this.autoreRepository.findAll());
		return "admin/manageAutori.html";
	}
	
	@PostMapping("/admin/autore")
	public String newAutore(@ModelAttribute("autore") Autore autore, Model model) {
		if(!autoreRepository.existsByNomeAndCognome(autore.getNome(), autore.getCognome())) {
			this.autoreRepository.save(autore);
			model.addAttribute("autore", autore);
			return "autore.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo autore esiste gia");
			return "admin/formNewAutore.html";
		}
	}
	
	@GetMapping("/autore")
	public String getAutori(Model model) {
		model.addAttribute("autori", this.autoreRepository.findAll());
		return "autori.html";
	}
	
	@GetMapping("/autore/{id}")
	public String getAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", this.autoreRepository.findById(id).get());
		return "autore.html";
	}
	
	@GetMapping(value="/admin/formUpdateAutori/{id}")
	public String formUpdateAutore(@PathVariable("id") Long id, Model model) {
		model.addAttribute("autore", autoreService.getAutoreById(id));
		return "admin/formUpdateAutore.html";
	}
	
	@PostMapping(value="/admin/updateAutore/{autoreId}")
	public String updateAutore(@ModelAttribute("autore") Autore newAutore, @PathVariable("autoreId") Long autoreId, Model model) {
		Autore oldAutore = this.autoreService.getAutoreById(autoreId); // prende l'id del vecchio autore
		Autore autore = this.autoreService.updateAutore(oldAutore, newAutore);
		this.autoreService.saveAutore(autore);
		model.addAttribute("autore", autore);
		return "admin/formUpdateAutore.html";
	}

}
