package br.senai.sp.caroba.clothesguide.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.caroba.clothesguide.annotation.Publico;
import br.senai.sp.caroba.clothesguide.model.Avaliacao;
import br.senai.sp.caroba.clothesguide.repository.AvaliacaoRepository;

@RestController
@RequestMapping(value = "/api/avaliacao")
public class AvaliacaoRestController {
	
	@Autowired
	private AvaliacaoRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/" + avaliacao.getId())).body(avaliacao);
	}
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Avaliacao> getAvaliacoes(){
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getAvaliacao(@PathVariable("id") Long id){
		return repository.findById(id).get();
	}
	
	@RequestMapping(value = "/loja/{idLoja}", method = RequestMethod.GET)
	public Iterable<Avaliacao> getAvaliacaoByLoja(@PathVariable("idLoja") Long idLoja){
		return repository.findAllByLojaId(idLoja);
	}
	
	
}
