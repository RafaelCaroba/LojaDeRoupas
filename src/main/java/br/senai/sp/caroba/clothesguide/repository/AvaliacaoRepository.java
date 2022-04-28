package br.senai.sp.caroba.clothesguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.caroba.clothesguide.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	public List<Avaliacao> findAllByLojaId(Long idLoja);
}
