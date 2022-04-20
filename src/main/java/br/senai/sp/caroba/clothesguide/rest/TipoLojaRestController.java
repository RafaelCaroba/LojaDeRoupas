package br.senai.sp.caroba.clothesguide.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.caroba.clothesguide.annotation.Publico;
import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;
import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;

@RequestMapping(value = "/api/tipoLoja")
@RestController
public class TipoLojaRestController {
	
	@Autowired
	private TipoLojaRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<TipoLojaRoupa> getTipos(){
		return repository.findAll();
	}
}
