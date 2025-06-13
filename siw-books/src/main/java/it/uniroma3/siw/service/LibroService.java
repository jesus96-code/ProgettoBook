package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Autore;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.AutoreRepository;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class LibroService {
	
	@Autowired
	private RecensioneService recensioneService;

	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private AutoreRepository autoreRepository;

	@Autowired
	private AutoreService autoreService;
	
	public Libro getLibroById(Long id) {
		return this.libroRepository.findById(id).get();
	}
	
	@Transactional
	public void createNewLibro(Libro libro) {
		this.libroRepository.save(libro);
	}
	
	@Transactional
	public Libro updateLibro(Libro oldLibro, Libro newLibro) {
		oldLibro.setTitolo(newLibro.getTitolo());
		oldLibro.setAnnoPubblicazione(newLibro.getAnnoPubblicazione());
		return this.libroRepository.save(oldLibro);
	}

	public void saveLibro(@Valid Libro libro) {
		this.libroRepository.save(libro);
	}
	
	@Transactional
	public Libro addAutoreToLibro(Long libroId, Long autoreId) {
		Libro libro = this.libroRepository.findById(libroId).get();
		Autore autore = this.autoreRepository.findById(autoreId).get();
		List<Autore> autori = libro.getAutori();
		autori.add(autore);
		this.libroRepository.save(libro);
		return libro;
	}
	
	public List<Autore> findAutoriNotLibro(Long libroId){
		List<Autore> autoriToAdd = new ArrayList<>();
		
		for (Autore a : autoreService.findAutoriNotInLibro(libroId)) {
			autoriToAdd.add(a);
		}
		return autoriToAdd;
	}
	
	@Transactional
	public Libro removeAutoreFromLibro(Long libroId, Long autoreId) {
		Libro libro = this.getLibroById(libroId);
		Autore autore = this.autoreService.getAutoreById(autoreId);
		List<Autore> autori = libro.getAutori();
		autori.remove(autore);
		this.libroRepository.save(libro);
		return libro;
	}
	
	@Transactional
	public Libro addRecensioneToLibro(Long libroId, Long recensioneId) {
		Libro libro = this.getLibroById(libroId);
		Recensione recensione = this.recensioneService.getRecensioneById(recensioneId);
		libro.getRecensioni().add(recensione);
		return this.libroRepository.save(libro);
	}
}
