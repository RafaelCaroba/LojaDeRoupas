package br.senai.sp.caroba.clothesguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.caroba.clothesguide.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// var para descobrir onde o user está tentando ir
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		// verifica se o handler é um handler method
		// indica que foi encontrado algum método no controller 
		// para a req.
		
		if (handler instanceof HandlerMethod) {
			// liberar o acesso à pagina inicial
		
			if (uri.equals("/")) {
				return true;
			}
			if (uri.endsWith("/error")) {
				return true;
			}
			// fazer casting para handlreMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			
			// se o método for publico, libera
			if (metodoChamado.getMethodAnnotation(Publico.class) !=null) {
				return true;
			}
			// verificar se existe um user logado
			if (request.getSession().getAttribute("usuarioLogado") != null) {
				return true;
			} else {
				// red. para a pag inicial
				response.sendRedirect("/");
				return false;
			}
		}
		return true;
	}
}
