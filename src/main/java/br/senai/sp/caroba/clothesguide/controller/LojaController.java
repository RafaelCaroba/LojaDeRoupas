package br.senai.sp.caroba.clothesguide.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.caroba.clothesguide.model.Loja;
import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;
import br.senai.sp.caroba.clothesguide.repository.LojaRepository;
import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;

@Controller
public class LojaController {
	
	@Autowired
	private TipoLojaRepository tipoRepo;
	
	@Autowired
	private LojaRepository lojaRepo;
	
	@RequestMapping(value = "formLoja")
	public String form(Model model) {
		model.addAttribute("tipos", tipoRepo.findAllByOrderByNomeAsc());
		return "loja/formLoja";
	}
	
	@RequestMapping(value = "salvarLoja")
	public String salvarLoja(Loja loja,@RequestParam("fileFotos") MultipartFile[] fileFotos) {
//		System.out.println(fileFotos.length);
		lojaRepo.save(loja);
		return"redirect:formLoja";
	}
}
