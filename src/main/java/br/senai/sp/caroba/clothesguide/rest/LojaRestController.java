package br.senai.sp.caroba.clothesguide.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.caroba.clothesguide.annotation.Publico;
import br.senai.sp.caroba.clothesguide.model.Loja;
import br.senai.sp.caroba.clothesguide.repository.LojaRepository;


@RequestMapping(value = "/api/loja")
@RestController
public class LojaRestController {
	
	@Autowired
	private LojaRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Loja> getLojas(){
		return repository.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Loja> findLoja(@PathVariable("id") Long idLoja){
		// busca o restaurante
		Optional<Loja> loja = repository.findById(idLoja);
		if (loja.isPresent()) {
			return ResponseEntity.ok(loja.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Publico
	@RequestMapping(value = "/tipo/{tipoId}", method = RequestMethod.GET)
	public Iterable<Loja> findLojaByTipo(@PathVariable("tipoId") Long idTipo){
		return repository.findByTipoLojaId(idTipo);
	}
	
	@Publico
	@RequestMapping(value = "/pagPix/{pagPix}", method = RequestMethod.GET)
	public Iterable<Loja> findLojaWithPix(@PathVariable("pagPix") boolean pagPix){
		return repository.findByPagPix(pagPix);
	}
	
	@Publico
	@RequestMapping(value = "/estado/{siglaEstado}")
	public Iterable<Loja> findLojaByEstado(@PathVariable("siglaEstado") String estado){
		return repository.findByEstado(estado);
	}
}
