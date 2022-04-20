package br.senai.sp.caroba.clothesguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.caroba.clothesguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
	
}
