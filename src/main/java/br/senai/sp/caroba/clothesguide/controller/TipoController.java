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
import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;
import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;

@Controller
public class TipoController {
	// repository para a persistência do Admin
	// AutoWired paa gerar a referência
	@Autowired
	private TipoLojaRepository repo;

	@RequestMapping(value = "formTipoLoja")
	public String formTipo() {
		return "tipoLoja/formTipo";
	}

	@RequestMapping(value = "salvarTipoLoja", method = RequestMethod.POST)
	public String salvarTipo(@Valid TipoLojaRoupa tipo, BindingResult result, RedirectAttributes attr) {

		// validação de campos
		if (result.hasErrors()) {
			// mensagem de erro na req
			attr.addFlashAttribute("mensagemErro", "Verifique os campos.");
			return "redirect:formTipoLoja";
		} else {
			// salvar o cadastro
			try {
				repo.save(tipo);
				attr.addFlashAttribute("mensagemSucesso", "Cadastro efetuado com sucesso! " + "Id: " + tipo.getId());
			} catch (Exception e) {
				e.printStackTrace();
				// informar erro ao usuário
				attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: " + e.getMessage());
			}

		}
		return "redirect:formTipoLoja";
	}

	// request mapping para a lista, informando a pagina desejada
	@RequestMapping(value = "listarTipoLoja/{qtdItens}/{page}")
	public String listarTipo(Model model, @PathVariable("page") int page, @PathVariable("qtdItens") int qtdItens) {
		// cria um pageble com 6 elementos por página, ordenando pelo nome e de forma
		// ascendente
		PageRequest pageable = PageRequest.of(page - 1, qtdItens, Sort.by(Sort.Direction.ASC, "nome"));
		// cria a página atual através do repository
		Page<TipoLojaRoupa> pagina = repo.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		// cria uma lista de inteiros para reprsentar as páginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		// preencher a lista com páginas
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		model.addAttribute("tipos", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		// retorna para o html da lista
		return "tipoLoja/listaTipos";
	}

	@RequestMapping(value = "alterarTipoLoja")
	public String alterarTipo(Model model, Long id) {
		TipoLojaRoupa tipoLoja = repo.findById(id).get();
		model.addAttribute("tipo", tipoLoja);
		return "forward:formTipoLoja";
	}

	@RequestMapping(value = "excluirTipoLoja")
	public String excluirTipoLoja(Long id, int page) {
		repo.deleteById(id);
		return "redirect:listarTipoLoja/" + page;
	}

	@RequestMapping(value = "/buscarTipo")
		public String buscarTipo(Model model, String palavraChave, String select) {
			
			if (select.equals("palavraChave")) {
				// método de buscar lá no repository e retorna a lista com o tipo
				model.addAttribute("tipos", repo.procurarPalavraChave(palavraChave));
				System.out.println(palavraChave);
				return "tipoLoja/listaTipos";
			}else if(select.equals("nome")){
				model.addAttribute("tipos", repo.procurarNome(palavraChave));
				return "tipoLoja/listaTipos";
			}else {
				model.addAttribute("tipos", repo.procurarDescricao(palavraChave));
				return "tipoLoja/listaTipos";
			}
			
			
		}
}
