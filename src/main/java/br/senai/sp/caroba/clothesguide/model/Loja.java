package br.senai.sp.caroba.clothesguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Loja {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private TipoLojaRoupa tipoLoja;
	private String nomeLoja;
	
	@Column(columnDefinition = "varchar(11)")
	private String cep;
	private String endereco;
	private String numero;
	private String bairro;
	private String complemento;
	private String cidade;
	private String estado;
	private boolean formasPagamento;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	
	private String site;
	private String redesSociais;
	private String telefone;
	private String fotos;
	
	
}
