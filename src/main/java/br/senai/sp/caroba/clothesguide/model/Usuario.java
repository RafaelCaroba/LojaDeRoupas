package br.senai.sp.caroba.clothesguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.senai.sp.caroba.clothesguide.util.HashUtil;
import lombok.Data;

@Entity
@Data
public class Usuario {
	private Long id;
	private String nome;
	
	@Column(unique = true)
	private String email;
	private String senha;
	
	public void setSenha(String senha) {
		this.senha = HashUtil.hash256(senha);
	}
}
