package br.senai.sp.caroba.clothesguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TipoLojaRoupa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "VARCHAR(50)", nullable = false)
	private String nome;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String descricao;
	
	@Column(columnDefinition = "VARCHAR(30)", nullable = false)
	private String palavrasChave;
}
