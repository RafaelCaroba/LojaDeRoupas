package br.senai.sp.caroba.clothesguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.caroba.clothesguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	public Administrador findByEmailAndSenha(String email, String senha);
}
