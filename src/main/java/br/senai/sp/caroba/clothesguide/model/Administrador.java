package br.senai.sp.caroba.clothesguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.caroba.clothesguide.util.HashUtil;
import lombok.Data;

// para gerar gets e sets
@Data
// para mapear como uma entidade (tabela) no banco
@Entity
public class Administrador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty // dá pra inserir uma mensagem de validação usando os () e aspas
	private String nome;
	
	@Email
	// emails não têm mais de um valor igual
	@Column(unique = true)
	private String email;
	
	@NotEmpty
	private String senha;
	
	
	
	// sobrescrevendo método para setar senha usando hash
	public void setSenha(String senha) {
		// aplica o hash já setando a senha no objeto Administrador
		this.senha = HashUtil.hash256(senha);
	}
	
	// método para setar a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
		// seta o hash na senha
		this.senha = hash;
	}
}
