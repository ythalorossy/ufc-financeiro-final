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

import org.hibernate.Query;

import br.com.entities.AplicacaoEntity;
import br.com.entities.PermissaoUsuarioEntity;
import br.com.entities.UpaEntity;
import br.com.entities.UsuarioEntity;
import br.seguranca.dao.DaoUpa;

import com.hibernate.HibernateHelperUsuario;

public class ValidaLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		UsuarioEntity usuario = (UsuarioEntity) httpRequest.getSession().getAttribute("usuario");
		
		if (usuario == null) {
			
			if ((usuario = validaUsuario(httpRequest)) != null) {
				
				HttpSession session = httpRequest.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				
				session = httpRequest.getSession();
				session.setAttribute("usuario", usuario);
				
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/logon.do?operacao=login");	
			
			} else {
				
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
			}
			
		}else {
					chain.doFilter(request, response);
			
		}
		
	}

	public void init(FilterConfig config) throws ServletException {

		System.out.println("Inicializando complexo sistema de verificacao de login...");
		
	}

	@SuppressWarnings("unchecked")
	public UsuarioEntity validaUsuario(HttpServletRequest request) {
		
		UsuarioEntity retorno = null;
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		String login = request.getParameter("username");
		String password = null;
		try {
			password = request.getParameter("password");
		} catch (Exception e) {
			
		}
		

		if(login != null && password != null){
			if (!login.equals("") && !password.equals("")){
				HibernateHelperUsuario hu = HibernateHelperUsuario.getInstance();
				hu.getSession().beginTransaction();
				Query query = hu.getSession().createQuery("from UsuarioEntity where usuario = ? and senha = ?");
				query.setParameter(0, login);
				query.setParameter(1, password);
				List<UsuarioEntity> list = query.list();
				hu.getSession().getTransaction().commit();
				hu.close();

				if (!list.isEmpty()) {
					retorno = list.get(0);
				}
			}
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public static boolean verificaPermissao(String permissao,HttpServletRequest request, String aplicacao)
	throws Exception {

		boolean retorno = false;
		UsuarioEntity usuario = new UsuarioEntity();
//		Tentativa de recuperar o paramentro ou atributo Usuario
		try{
			usuario = (UsuarioEntity) request.getSession().getAttribute("usuario");
		}catch (Exception e) {

		}
//		Caso o id do usuario seja diferente de zero
		if(usuario.getId() != 0){
			final PermissaoUsuarioEntity pu = (PermissaoUsuarioEntity) request.getSession().getAttribute("permissaoUsuario");
			final AplicacaoEntity ap = (AplicacaoEntity) request.getSession().getAttribute("aplicacao");
			// Buscando as aplicações permitidas para o usuario
			final List<UpaEntity> upa = new DaoUpa().findUpaByUsuarioAplicacao(pu.getId(), ap.getId());
			for (int i = 0; i < upa.size(); i++){
				if (upa.get(i).getPermissao().getDescricao().equals(permissao)){
					retorno = true;
					break;
				}
			}
		}

		return retorno;
	}

	
}
