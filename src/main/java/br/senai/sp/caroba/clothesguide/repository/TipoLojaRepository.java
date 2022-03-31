package br.senai.sp.caroba.clothesguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;

public interface TipoLojaRepository extends PagingAndSortingRepository<TipoLojaRoupa, Long>{
	
	@Query("SELECT t FROM TipoLojaRoupa t WHERE t.palavrasChave LIKE %:pc%")
	public List<TipoLojaRoupa> procurarPalavraChave(@Param("pc") String palavraChave);
	
	@Query("SELECT t FROM TipoLojaRoupa t WHERE t.nome LIKE %:n%")
	public List<TipoLojaRoupa> procurarNome(@Param("n") String palavraChave);
	
	@Query("SELECT t FROM TipoLojaRoupa t WHERE t.descricao LIKE %:desc%")
	public List<TipoLojaRoupa> procurarDescricao(@Param("desc") String palavraChave);


}
