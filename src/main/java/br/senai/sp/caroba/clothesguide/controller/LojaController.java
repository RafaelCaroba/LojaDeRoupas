package br.senai.sp.caroba.clothesguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;

@Controller
public class LojaController {
	
	@Autowired
	private TipoLojaRepository tipoRepo;
	
	@RequestMapping(value = "formLoja")
	public String form(Model model) {
		model.addAttribute("tipos", tipoRepo.findAllByOrderByNomeAsc());
		return "loja/formLoja";
	}
}
