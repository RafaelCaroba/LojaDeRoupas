package br.senai.sp.caroba.clothesguide.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.caroba.clothesguide.model.Usuario;
import br.senai.sp.caroba.clothesguide.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> criarUsuario(Usuario usuario){
		return null;
	}
}
