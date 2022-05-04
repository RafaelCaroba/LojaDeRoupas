package br.senai.sp.caroba.clothesguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.caroba.clothesguide.annotation.Privado;
import br.senai.sp.caroba.clothesguide.annotation.Publico;
import br.senai.sp.caroba.clothesguide.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor {
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

			if (uri.startsWith("/api")) {
				// quando a req começar com /api
				// var para o token
				String token = null;

				// se for um metodo privado
				if (metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
						// obtém o token da req
						token = request.getHeader("Authorization");
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR)
								.build();
						DecodedJWT jwt = verifier.verify(token);

						// extrair os dados do payload
						Map<String, Claim> payload = jwt.getClaims();
						return true;
					} catch (Exception e) {
						if (token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						} else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}

				}

				return true;
			} else {

				// se o método for publico, libera
				if (metodoChamado.getMethodAnnotation(Publico.class) != null) {
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
		}
		return true;
	}
}
