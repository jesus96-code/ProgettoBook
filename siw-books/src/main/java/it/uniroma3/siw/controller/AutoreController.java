package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.repository.AutoreRepository;

@Controller
public class AutoreController {
	
	@Autowired
	private AutoreRepository autoreRepository;
	
	@GetMapping(value = "/admin/indexAutore")
	public String indexAutore() {
		return "admin/indexAutore.html";
	}
	
	@GetMapping(value="/admin/formNewAutore")
	public String formNewAutore(Model model) {
		model.addAttribute("autore", new Autore());
		return "admin/formNewAutore.html";
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
}
