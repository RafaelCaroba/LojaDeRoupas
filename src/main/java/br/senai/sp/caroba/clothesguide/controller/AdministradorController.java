package br.senai.sp.caroba.clothesguide.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.caroba.clothesguide.model.Administrador;
import br.senai.sp.caroba.clothesguide.repository.AdminRepository;

@Controller
public class AdministradorController {
	// repository para a persistência do Admin
	//AutoWired paa gerar a referência
	@Autowired
	private AdminRepository repo;
	
	
	@RequestMapping(value = "formAdm")
	public String formAdm() {
		return "administrador/formAdm";
	}
	
	@RequestMapping(value = "salvarAdm", method = RequestMethod.POST)
	public String salvarAdm(@Valid Administrador adm, BindingResult result, RedirectAttributes attr) {
		// verifica se houve erro na validação do objeto
		if (result.hasErrors()) {
			// envia mensagem de erro via requisição 
			attr.addFlashAttribute("mensagemErro", "Verifique os campos.");
			return "redirect:formAdm";
		}
		try {
			// salvar o Admin
			repo.save(adm);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso! Id: "+adm.getId());
			
		} catch (Exception e) {
			// caso haja algum erro, informa ao usuário
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: "+e.getMessage());
		}
		return"redirect:formAdm";
	}
}
