package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.LibroService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class RecensioneController {
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private RecensioneService recensioneService;
	
	@GetMapping("/user/formNewRecensione/{id}")
	public String formNewRecensione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("libro", this.libroService.getLibroById(id));
		model.addAttribute("recensione", new Recensione());
		return "user/formNewRecensione.html";
	}
	
	@PostMapping("/user/recensione/{libroId}")
	public String newRecensione(@Valid @ModelAttribute Recensione recensione, BindingResult bindingResult,
									@PathVariable("libroId") Long libroId, Model model) {
		if(!bindingResult.hasErrors()) {
			Recensione newRecensione = this.recensioneService.newRecensione(recensione, libroId);
			Libro libro = this.libroService.addRecensioneToLibro(libroId, newRecensione.getId());
			model.addAttribute("libro", libro);
			model.addAttribute("recensione", newRecensione);
			return "user/recensioneSuccessful.html";
		} else {
			model.addAttribute("libro", this.libroService.getLibroById(libroId));
			return "user/formNewRecensione.html";
		}
	}
}
