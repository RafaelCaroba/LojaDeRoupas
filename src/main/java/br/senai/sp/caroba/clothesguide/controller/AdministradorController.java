package br.senai.sp.caroba.clothesguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.caroba.clothesguide.model.Administrador;
import br.senai.sp.caroba.clothesguide.repository.AdminRepository;
import br.senai.sp.caroba.clothesguide.util.HashUtil;

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
		
		// verifia se está ocorrendo uma alteração de cadastro
		boolean alteracao = adm.getId() != null ? true : false;
		
		// verifica se a senha está vazia
		if (adm.getSenha().equals(HashUtil.hash256(""))) {
			// caso não seja alteração, define a primeira parte do email como senha
			if (!alteracao) {
				// extrai a parte do email antes do @
				String parte = adm.getEmail().substring(0, adm.getEmail().indexOf("@"));
				// define a senha do admin
				adm.setSenha(parte);
			} else {
				// busca a senha atual
			String hash = repo.findById(adm.getId()).get().getSenha();
			// set a senha com hash
			adm.setSenhaComHash(hash);
			}
		}
		
		try {
			// salvar o Admin
			repo.save(adm);
			attr.addFlashAttribute("mensagemSucesso", "Administrador salvo com sucesso! "
					+ "Caso a senha não tenha sido informada no cadastro, será a parte do e-mail antes do @ "
					+ "Id: "+adm.getId());
			
		} catch (Exception e) {
			// caso haja algum erro, informa ao usuário
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: "+e.getMessage());
		}
		return"redirect:formAdm";
	}
	
	// request mapping para a lista, informando a pagina desejada
	@RequestMapping(value = "listaAdm/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// cria um pageble com 6 elementos por página, ordenando pelo nome e de forma ascendente
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// cria a página atual através do repository
		Page<Administrador> pagina = repo.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		// cria uma lista de inteiros para reprsentar as páginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		// preencher a lista com páginas
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas" ,pageNumbers);
		// retorna para o html da lista
		return "administrador/listaAdm";
	}
	
	@RequestMapping(value = "alterarAdm")
	public String alterarAdm(Model model, Long id) {
		Administrador adm = repo.findById(id).get();
		model.addAttribute("adm", adm);
		return "forward:formAdm";
	}
	
	@RequestMapping(value = "excluirAdm")
	public String excluirAdm(Long id, int page) {
		repo.deleteById(id);
		return "redirect:listaAdm/1";
	}
	
	
}
