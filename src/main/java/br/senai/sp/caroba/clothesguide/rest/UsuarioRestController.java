package br.senai.sp.caroba.clothesguide.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.senai.sp.caroba.clothesguide.annotation.Privado;
import br.senai.sp.caroba.clothesguide.annotation.Publico;
import br.senai.sp.caroba.clothesguide.model.Erro;
import br.senai.sp.caroba.clothesguide.model.Sucesso;
import br.senai.sp.caroba.clothesguide.model.TokenJWT;
import br.senai.sp.caroba.clothesguide.model.Usuario;
import br.senai.sp.caroba.clothesguide.repository.UsuarioRepository;


@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioRestController {
	
	// constantes para geração de token
	public static final String EMISSOR = "Senai";
	public static final String SECRET = "Cl0thesGu1de";
	

	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Usuario> getLojas(){
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		
		try {
			// salvar user no bd
			repository.save(usuario);
			// retorna o cód 201 com a URL para acesso no location e o usuario inserido
			return ResponseEntity.created(URI.create("/"+usuario.getId())).body(usuario);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constraint: Registro duplicado.");
			erro.setException(e.getClass().getName());
			
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constraint: " + e.getMessage());
			erro.setException(e.getClass().getName());
			
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id){
		// valida o id
		if (id != usuario.getId()) {
			throw new RuntimeException("Id inválido");
		}
		// salva o usuario no banco
		repository.save(usuario);
		
		// criar um cabeçalho http
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("api/usuario/"));
		
		Sucesso msg = new Sucesso();
		msg.setStatusCode(200);
		msg.setMensagem("Atualizaçaõ de Usuário bem-sucedida.");
		
		
		return new ResponseEntity<Object>(msg, header, HttpStatus.OK);
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findUser(@PathVariable("id") Long idUsuario){
		Optional<Usuario> usuario = repository.findById(idUsuario);
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		} else {
			return ResponseEntity.notFound().build();
		}	
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> excluirUsuario(@PathVariable("id") Long id){
		repository.deleteById(id);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("api/usuario/"));
		
		Sucesso msg = new Sucesso();
		msg.setStatusCode(204);
		msg.setMensagem("Usuário deletado com sucesso.");
		
		return new ResponseEntity<Object>(msg, header, HttpStatus.OK);
	}
	
	@Publico
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenJWT> logar(@RequestBody Usuario usuario){
		// buscar o usuario no BD
		usuario = repository.findByEmailAndSenha(usuario.getEmail(),usuario.getSenha());
		
		// verifica se o usuario existe
		if (usuario != null) {
			// valores adicionais para o token
			Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("id_usuario", usuario.getId());
			payload.put("nome_usuario", usuario.getNome());	
			
			// definir a data de expiração do token
			Calendar expiracao = Calendar.getInstance();
			expiracao.add(Calendar.HOUR, 1);
			
			// algoritmo para assinar o token
			Algorithm algoritmo = Algorithm.HMAC256(SECRET);
			
			// gerar o token
			TokenJWT tokenJwt = new TokenJWT();
			tokenJwt.setToken
			(JWT.create().withPayload(payload).withIssuer(EMISSOR)
					.withExpiresAt(expiracao.getTime()).sign(algoritmo));
			
			return ResponseEntity.ok(tokenJwt);
			
		} else {
			return new ResponseEntity<TokenJWT>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
