package br.senai.sp.caroba.clothesguide.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	private boolean pagDinheiro;
	private boolean pagCredito;
	private boolean pagDebito;
	private boolean pagPix;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	
	private String site;
	private String redesSociais;
	private String telefone;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	
	@OneToMany(mappedBy = "loja")
	private List<Avaliacao> avaliacoes;
	
	
	
	public String[] verFotos() {
		return this.fotos.split(";");
	}
	
	
}
