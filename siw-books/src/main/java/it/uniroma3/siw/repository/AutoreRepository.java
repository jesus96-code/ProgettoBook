package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Autore;

public interface AutoreRepository extends CrudRepository<Autore, Long>{
	
	@Query(value = "SELECT * FROM autore a " + //prendi tutti gli attori
            "WHERE a.id NOT IN (" +    //esclude quelli che sono già legati a quel libro
            "  SELECT autore_id FROM libro_autore " +  //trova gli attori che hanno già scritto quel libro
            "  WHERE libro_id = :libroId)", nativeQuery = true)
	public Iterable<Autore> findAutoriNotInLibro(@Param("libroId") Long id);

	public boolean existsByNomeAndCognome(String nome, String cognome);
}
