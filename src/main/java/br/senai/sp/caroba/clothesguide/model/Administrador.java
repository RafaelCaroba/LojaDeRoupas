package br.senai.sp.caroba.clothesguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

// para gerar gets e sets
@Data
// para mapear como uma entidade (tabela) no banco
@Entity
public class Administrador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;
	
	@Email
	// emails não têm mais de um valor igual
	@Column(unique = true)
	private String email;
	
	@NotEmpty
	private String senha;
}
