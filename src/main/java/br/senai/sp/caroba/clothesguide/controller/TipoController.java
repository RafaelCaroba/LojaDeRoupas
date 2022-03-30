package br.senai.sp.caroba.clothesguide.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;
import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;

@Controller
public class TipoController {
	// repository para a persistência do Admin
		//AutoWired paa gerar a referência
	@Autowired
	private TipoLojaRepository repo;
	
	

	@RequestMapping(value = "formTipoLoja")
	public String formTipo() {
		return "tipoLoja/formTipo";
	}
	
	@RequestMapping(value = "salvarTipoLoja")
	public String salvarTipo(TipoLojaRoupa tipo, BindingResult result, RedirectAttributes attr) {
		// validação de campos
		if (result.hasErrors()) {
			// mensagem de erro na req
			attr.addFlashAttribute("mensagemErro", "Verifique os campos.");
			return "redirect:formTipoLoja";
		} else {
			// salvar o cadastro
			try {
				repo.save(tipo);
				attr.addFlashAttribute("mensagemSucesso", "Cadastro efetuado com sucesso! "
						+ "Id: "+tipo.getId());
			} catch (Exception e) {
				e.printStackTrace();
				// informar erro ao usuário
				attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: "+e.getMessage());
			}
			
		}
		return "redirect:formTipoLoja";
	}
}
