package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends CrudRepository<Libro, Long>{
	
	public List<Libro> findByAnnoPubblicazione(int annoPubblicazione);
	
	public List<Libro> findByTitolo(String titolo);
	
	public boolean existsByTitoloAndAnnoPubblicazione(String titolo, int annoPubblicazione);
	
	// metodo che ordina i film in base all'anno
	public List<Libro> findByOrderByAnnoPubblicazioneAsc();
	
	//metodo che ordina i film in base al titolo
	public List<Libro> findByOrderByTitoloAsc();
}
