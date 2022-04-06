package br.senai.sp.caroba.clothesguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.caroba.clothesguide.model.Loja;


public interface LojaRepository extends PagingAndSortingRepository<Loja, Long> {

	
}
