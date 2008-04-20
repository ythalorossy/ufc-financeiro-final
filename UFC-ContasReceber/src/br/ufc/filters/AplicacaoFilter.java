package br.ufc.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.entities.PermissaoUsuarioEntity;
import br.com.entities.UpaEntity;
import br.com.entities.UsuarioEntity;
import br.seguranca.dao.DaoPermissaoUsuario;
import br.seguranca.dao.DaoUpa;
import br.seguranca.dao.DaoUser;

public class AplicacaoFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		final String aplicacao = "Financeiro";
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
				
		boolean retorno = false;
		UsuarioEntity usuario = (UsuarioEntity) httpRequest.getSession().getAttribute("usuario");

		if ((usuario ==null)) {
			String id = request.getParameter("usuario");
			usuario = new DaoUser().findById(Integer.parseInt(id));
		}

		if(usuario.getId() != 0){

			try {
				final PermissaoUsuarioEntity pu = new DaoPermissaoUsuario().findByIdUsuario(usuario.getId());
				if(pu != null){
					final List<UpaEntity> upa = new DaoUpa().findUpaByUsuarioId(pu.getId());
					for(int i = 0; i < upa.size(); i++){
						if(upa.get(i).getAplicacao().getNome().equals(aplicacao)){
							httpRequest.getSession().setAttribute("aplicacao", upa.get(i).getAplicacao());
							retorno = true;
							break;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if (retorno){
			chain.doFilter(request, response);
		} else{
			HttpSession session = httpRequest.getSession(false);
			if (session != null){
				session.invalidate();
			}
			httpResponse.sendRedirect("http://localhost:8080/Permissao");
		}

		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("Iniciando Filtro de Aplicações");
		
	}

}
