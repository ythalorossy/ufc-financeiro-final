package br.ufc.uteis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Auxiliar.Clientes;
import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;

import br.ufc.DAO.ClientesDAO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;

/**
 * Servlet implementation class for Servlet: AjaxUteis
 * 
 */
public class AjaxUteis extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;

	public AjaxUteis() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Dispara para ser tratado no metodo Post
		 */
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		final String tipoRetorno = request.getParameter("tipoRetorno");

		if (tipoRetorno.equals("laboratorio")) {
			requisitaLaboratorio(request, response);
		}
		
		if (tipoRetorno.equals("cliente")) {
			requisitaCliente(request, response);
		}

	}

	private void requisitaCliente(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		final String prefix = request.getParameter("prefix");

		List<Clientes> list = new ClientesDAO().findByPrefixCGCPF(prefix);

		if (!list.isEmpty()) {

			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();

			for (Clientes l : list) {
				out.print("<option value=\"" + l.getId() + "\">" + l.getNome()+ "</option>");
			}
			out.flush();
		}
		
	}

	private void requisitaLaboratorio(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		final int idDivisao = Integer.parseInt(request
				.getParameter("idDivisao"));
		final Divisao divisao = new DivisaoDAO().findById(idDivisao);

		List<Laboratorio> list = new LaboratorioDAO().findByIdDivisao(divisao);

		if (!list.isEmpty()) {

			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();

			for (Laboratorio l : list) {
				out.print("<option value=\"" + l.getId() + "\">" + l.getNome()+ "</option>");
			}
			out.flush();
		}
	}

}