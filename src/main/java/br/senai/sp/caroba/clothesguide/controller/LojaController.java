package br.senai.sp.caroba.clothesguide.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.senai.sp.caroba.clothesguide.model.Loja;
import br.senai.sp.caroba.clothesguide.model.TipoLojaRoupa;
import br.senai.sp.caroba.clothesguide.repository.LojaRepository;
import br.senai.sp.caroba.clothesguide.repository.TipoLojaRepository;
import br.senai.sp.caroba.clothesguide.util.FirebaseUtil;

@Controller
public class LojaController {
	
	@Autowired
	private TipoLojaRepository tipoRepo;
	
	@Autowired
	private LojaRepository lojaRepo;
	
	@Autowired
	private FirebaseUtil firebaseUtil;
	
	@RequestMapping(value = "formLoja")
	public String form(Model model) {
		model.addAttribute("tipos", tipoRepo.findAllByOrderByNomeAsc());
		return "loja/formLoja";
	}
	
	@RequestMapping(value = "salvarLoja")
	public String salvarLoja(Loja loja,@RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// String para url das fotos
		String fotos = "";
		
		// percorrer o vetor de bytes de cada arquivo que for submetido pelo form
		for (MultipartFile arquivo : fileFotos) {
			// verificar se o arquivo está vazio
			if (arquivo.getOriginalFilename().isEmpty()) {
				continue;
			}
			// faz o upload para a nuvem e obtém a url gerada
			try {
				fotos += firebaseUtil.uploadFile(arquivo)+"";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		loja.setFotos(fotos);
//		System.out.println(fileFotos.length);
		lojaRepo.save(loja);
		return"redirect:formLoja";
	}
	
	@RequestMapping(value = "listarLoja/{qtdItens}/{page}")
	public String listarLojas(Model model, @PathVariable("qtdItens") int qtdItens,
			@PathVariable("page") int page) {
		
		PageRequest pageable = PageRequest.of(page - 1, qtdItens,
				Sort.by(Sort.Direction.ASC, "nomeLoja"));
		
		Page<Loja> pagina = lojaRepo.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for (int i = 0; i < totalPages; i++) {
			pageNumbers.add(i + 1);
		}
		
		model.addAttribute("lojas", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		// retorna para o html da lista
		return "loja/listaLoja";
	}
	
	@RequestMapping(value = "alterarLoja")
	public String alterarLoja(Model model, Long id) {
		Loja loja = lojaRepo.findById(id).get();
		model.addAttribute("loja", loja);
		return "forward:formLoja";
	}
	
	@RequestMapping(value = "excluirFotoLoja")
	public String excluirFoto(Long idLoja, int numFoto, Model model) {
		// buscar loja no BD
		Loja loja = lojaRepo.findById(idLoja).get();
		
		// pegar url da foto
		String fotoUrl = loja.verFotos()[numFoto];
		
		// excluir do firebase
		firebaseUtil.deletar(fotoUrl);
		
		loja.setFotos(loja.getFotos().replace(fotoUrl + ":", ""));
		
		// salvar obj no BD
		lojaRepo.save(loja);
		
		model.addAttribute("loja", loja);
		return "forward:formLoja";
	}
	
}
