package br.senai.sp.caroba.clothesguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.caroba.clothesguide.model.Loja;


public interface LojaRepository extends PagingAndSortingRepository<Loja, Long> {

	public List<Loja> findByTipoLojaId(Long idTipo);
	
	public List<Loja> findByPagPix(boolean pagPix);
	
	public List<Loja> findByEstado(String estado);
}
