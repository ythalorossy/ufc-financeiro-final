package br.ufc.action;

import java.sql.Connection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.Caixa;
import br.com.ContasPagar;
import br.com.ContasReceber;
import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.PedidoDespesa;
import br.ufc.BO.CaixaBO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.DAO.RelatorioDAO;
import br.ufc.TO.CaixaTO;
import br.ufc.TO.ContasPagarTO;
import br.ufc.TO.ContasReceberTO;
import br.ufc.TO.ItensNotaFiscalTO;
import br.ufc.TO.NotaFiscalTO;
import br.ufc.TO.PedidoDespesaTO;
import br.ufc.assembler.CaixaAssembler;
import br.ufc.assembler.ContasPagarAssembler;
import br.ufc.assembler.ContasReceberAssembler;
import br.ufc.assembler.ItensNotaFiscalAssembler;
import br.ufc.assembler.NotaFiscalAssembler;
import br.ufc.assembler.PedidoDespesaAssembler;
import br.ufc.uteis.Status;

import com.Auxiliar.Clientes;
import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;
import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

public class RelatorioAction extends DispatchAction{
	
	/*
	 * Todos os Métodos possuem o mesmo esquema para a geração do relatório
	 * Primeiro é recuperado do request o local real do arquivo compilado referente a cada relatório em específico
	 * Segundo é recuperado o nome do arquivo PDF que irá dar origem ao relatório
	 * Terceiro é recuperado as imagens pertencentes ao relatório
	 * 
	 * Após isto é realizado a inserção de itens em um Map. Este Map será utilizado como parametro para o relatório. Estes
	 * parametros serão tratados internamente ao relatório. Exemplo de paramentro é a imagem.
	 * 
	 * Apos feito isso é realizado a query no banco, trazendo uma coleção de itens que servirá como dataSource para o objeto
	 * JRBeanCollectionDataSource. Este Objeto é responsável por criar o relatório, ou seja, é dele que é executado a query interna
	 * ao relatório.
	 * 
	 * Feito isto é criado um impressor jasper (JasperPrint) e é chamado o metodo estático que preenche este relatório gerado pelo
	 * dataSource, utilizando o dataSource, o relatorio e os parametros recuperados do request.
	 * 
	 * Depois é criado o objeto "exportador", que irá exportar este relatório para o arquivo pdf referenciado como "saida" dentro do
	 * código. Este objeto é o objeto de Saída do relatorio, que será plotado na tela podendo este ser salvo, impresso ou enviado via
	 * email, caso o usuário assim deseje.
	 * 
	 * Vale lembrar que não é gerado um arquivo do relatório. Este arquivo pré-existente será sempre sobreposto e reutilizado pelo
	 * sistema. Não criando, com isso, um "inchaço" do sistema. 
	 * 
	 * Um método mais em particular é o metódo que gera o relatório caixa, pois este possui parametros adicionais que irão servir
	 * de base para a criação de informações sobre saldo atual, saldo anterior e saldo do dia.
	 * 
	 */
	private static final String LOAD_PAGINA = "loadPage";
	private static final String RELATORIO = "relatorio";
	
	private String imagemNutec(){
		final String imagem = getServlet().getServletContext().getRealPath("/imagens/Nutec.jpg");
		return imagem;
	}
	private String imagemGoverno(){
		final String imagem = getServlet().getServletContext().getRealPath("/imagens/governo.jpg");
		return imagem;
	}
	
	private String subDir(){
		final String subDir = getServlet().getServletContext().getRealPath("/jasper/");
		return subDir+"/";
	}
	
	private Connection getSubConnection(){
		Connection connection = null;
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/nutec","postgres", "1234");
		} catch (Exception e) {
			
		}
		
		return connection;
	}

	/**
	 * Método que prepara a tela para gerar os relatórios
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareRelatorio(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/*
		 * Preparando o relatório para listar as notas fiscais
		 */
		final List<NotaFiscal> notaFiscalList = NotaFiscalBO.getInstance().findAll();
		final List<NotaFiscalTO> notaFiscalTOList = NotaFiscalAssembler.getInstance().entity2EntityTO(notaFiscalList);
		request.setAttribute("notaFiscalTO", notaFiscalTOList);
		/*
		 * Preparando o relatório para listar os clientes
		 */
		final List<Clientes> clientesList = new ClientesDAO().findAll();
		request.setAttribute("clientes", clientesList);
		
		final List<Divisao> divisaoList = new DivisaoDAO().findAll();
		request.setAttribute("divisao", divisaoList);
		
		final List<PedidoDespesa> pedidoDespesaList = PedidoDespesaBO.getInstance().findAll();
		request.setAttribute("pd", pedidoDespesaList);
		
		final List<Laboratorio> laboratorioList = new LaboratorioDAO().findAll();
		request.setAttribute("laboratorio", laboratorioList);
		request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		return mapping.findForward("index");
	}

	/**
	 * Método que exibi as notas fiscais individualmente.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward relatorioNotaFiscalUnica(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * Aqui é recuperado o id da nota fiscal, localizado a nota fiscal, localizado o nome do cliente e o tipo de nota fiscal
		 * depois é setado os parametros necessarios para que o relatório seja exibido corretamente e é enviado para a pagina padrão do relatório
		 */
		final String idNotaFiscal = request.getParameter("idNotaFiscal");
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/notaFiscalUnica.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/notaFiscalUnica.pdf");
		
		final List<NotaFiscal> nf = RelatorioDAO.getInstance().relatorioNotaFiscalUnica(Integer.parseInt(idNotaFiscal));
		final List<NotaFiscalTO> to = new NotaFiscalAssembler().entity2EntityTO(nf);
		for (NotaFiscalTO notaFiscalTO : to) {
			notaFiscalTO.setIdCliente(notaFiscalTO.getNomeCliente());
			notaFiscalTO.setTipoNota(Status.retornaTipo(Integer.parseInt(notaFiscalTO.getTipoNota())));
			
		}
		// criação dos parametros
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subConnection", getSubConnection());
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("SUBREPORT_DIR",  subDir());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		saida = request.getContextPath()+("/relatorio/notaFiscalUnica.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);
	}

	/**
	 * Método que localiza todas as notas fiscais individualmente de um dado cliente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward relatorioNotaFiscalAnaliticaCliente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// Recuperando o id do cliente
		final String idCliente = request.getParameter("idCliente");
		// Localizando os arquivos necessarios para geração do relatório
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/notaFiscalAnaliticaCliente.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/notaFiscalUnica.pdf");

		double valorTotal = 0.0;

		// Criação do Objeto nota fiscal baseado em um cliente
		final List<NotaFiscal> nf = RelatorioDAO.getInstance().relatorioNotaFiscalUnicaCliente(idCliente);
		for (NotaFiscal notaFiscal : nf) {
			// Criação do valor total das notas fiscais por um cliente
			valorTotal += notaFiscal.getValorNota();
		}
		
		// Criação do objeto view
		final List<NotaFiscalTO> to = new NotaFiscalAssembler().entity2EntityTO(nf);
		// Inserção do nome e status do nota fiscal no objeto view
		for (NotaFiscalTO notaFiscalTO : to) {
			notaFiscalTO.setIdCliente(notaFiscalTO.getNomeCliente());
			notaFiscalTO.setTipoNota(Status.retornaTipo(Integer.parseInt(notaFiscalTO.getTipoNota())));
			
		}
		
		// Criação dos parametros
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subConnection", getSubConnection());
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("total", ConverteNumero.converteNumero(valorTotal));
		map.put("SUBREPORT_DIR", subDir());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		saida = request.getContextPath()+("/relatorio/notaFiscalUnica.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);
	}
	
	/**
	 * Método que localiza todas as notas fiscais individualmente de um dado cliente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward relatorioNotaFiscalSinteticoCliente(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// Recuperando o id do cliente
		final String idCliente = request.getParameter("idCliente");
		// Localizando os arquivos necessarios para geração do relatório
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/notaFiscalSinteticoCliente.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/notaFiscalUnica.pdf");

		double valorTotal = 0.0;

		// Criação do Objeto nota fiscal baseado em um cliente
		final List<NotaFiscal> nf = RelatorioDAO.getInstance().relatorioNotaFiscalUnicaCliente(idCliente);
		for (NotaFiscal notaFiscal : nf) {
			// Criação do valor total das notas fiscais por um cliente
			valorTotal += notaFiscal.getValorNota();
		}
		
		// Criação do objeto view
		final List<NotaFiscalTO> to = new NotaFiscalAssembler().entity2EntityTO(nf);
		// Inserção do nome e status do nota fiscal no objeto view
		for (NotaFiscalTO notaFiscalTO : to) {
			notaFiscalTO.setIdCliente(notaFiscalTO.getNomeCliente());
			notaFiscalTO.setTipoNota(Status.retornaTipo(Integer.parseInt(notaFiscalTO.getTipoNota())));
			
		}
		
		// Criação dos parametros
		
		final Map<String, String> map = new HashMap<String, String>();
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("total", ConverteNumero.converteNumero(valorTotal));
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		saida = request.getContextPath()+("/relatorio/notaFiscalUnica.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);
	}

	/**
	 * Método que localiza todas as notas fiscais em um determinado periodo
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward relatorioNotaFiscalSinteticoPeriodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final ActionMessages errors = new ActionMessages();

		// Recuperando datas
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");

		try {
			ConverteData.retornaData(dataFinal);
			ConverteData.retornaData(dataInicial);
		} catch (Exception e) {
			errors.add("dataInvalida", new ActionMessage("data.invalida.erro"));
		}

		if(errors.isEmpty()){
			final GregorianCalendar dateInit = ConverteData.retornaData(dataInicial);
			final GregorianCalendar dateEnd = ConverteData.retornaData(dataFinal);
			// Localizando os arquivos necessarios para geração do relatório
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/notaFiscalSinteticoCliente.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/notaFiscalUnica.pdf");

			double valorTotal = 0.0;

			// Criação do Objeto nota fiscal baseado em um cliente
			final List<NotaFiscal> nf = RelatorioDAO.getInstance().relatorioNotaFiscalUnicaPeriodo(dateInit, dateEnd);
			for (NotaFiscal notaFiscal : nf) {
				// Criação do valor total das notas fiscais por um cliente
				valorTotal += notaFiscal.getValorNota();
			}

			// Criação do objeto view
			final List<NotaFiscalTO> to = new NotaFiscalAssembler().entity2EntityTO(nf);
			// Inserção do nome e status do nota fiscal no objeto view
			for (NotaFiscalTO notaFiscalTO : to) {
				notaFiscalTO.setIdCliente(notaFiscalTO.getNomeCliente());
				notaFiscalTO.setTipoNota(Status.retornaTipo(Integer.parseInt(notaFiscalTO.getTipoNota())));

			}

			// Criação dos parametros

			final Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			saida = request.getContextPath()+("/relatorio/notaFiscalUnica.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		}else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);

	}

	public ActionForward relatorioNotaFiscal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/notaFiscal.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/notaFiscal.pdf");
		
		final List<NotaFiscal> nf = RelatorioDAO.getInstance().relatorioNotaFiscal();
		final List<NotaFiscalTO> to = new NotaFiscalAssembler().entity2EntityTO(nf);

		for (NotaFiscalTO notaFiscalTO : to) {
			notaFiscalTO.setIdCliente(notaFiscalTO.getNomeCliente());
			notaFiscalTO.setTipoNota(Status.retornaTipo(Integer.parseInt(notaFiscalTO.getTipoNota())));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subConnection", getSubConnection());
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("SUBREPORT_DIR", subDir());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		saida = request.getContextPath()+("/relatorio/notaFiscal.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);
	}
	
	public ActionForward relatorioContasReceberAnalitico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		final ActionMessages errors = new ActionMessages();
		
		try {
			ConverteData.retornaData(dataInicial);
			ConverteData.retornaData(dataFinal);
		} catch (Exception e) {
			errors.add("dataContaAnaliticoInvalida", new ActionMessage("data.invalida.erro"));
		}
		if (errors.isEmpty()){
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasReceberAnalitico.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/contasReceber.pdf");

			double valorTotal = 0.0;
			final List<ContasReceber> cr = RelatorioDAO.getInstance().relatorioContasReceber(ConverteData.retornaData(dataInicial), ConverteData.retornaData(dataFinal));
			for (ContasReceber contasReceber : cr) {
				valorTotal += contasReceber.getValorPrevista();
			}
			
			final List<ContasReceberTO> to = new ContasReceberAssembler().entity2EntityTO(cr);
			for (ContasReceberTO contasReceberTO : to) {
				final Clientes cliente = new ClientesDAO().findById(contasReceberTO.getIdCliente());

				contasReceberTO.setIdParcela(contasReceberTO.getNumeroParcela());
				contasReceberTO.setStatus(Status.retornaTipo(Integer.parseInt(contasReceberTO.getStatus())));

				contasReceberTO.setIdCliente(cliente.getNome());
				if (contasReceberTO.getValorEfetivo().equals("0,00")){
					contasReceberTO.setDataEfetiva("");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subConnection", getSubConnection());
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("SUBREPORT_DIR", subDir());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/contasReceber.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);
	}
	
	public ActionForward relatorioContasReceberSintetico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		final ActionMessages errors = new ActionMessages();
		
		try {
			ConverteData.retornaData(dataInicial);
			ConverteData.retornaData(dataFinal);
		} catch (Exception e) {
			errors.add("dataContaSinteticoInvalida", new ActionMessage("data.invalida.erro"));
		}
		if (errors.isEmpty()){
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasReceberSintetico.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/contasReceber.pdf");

			double valorTotal = 0.0;
			double valorPago = 0.0;
			double valorAberto = 0.0;
			final List<ContasReceber> cr = RelatorioDAO.getInstance().relatorioContasReceber(ConverteData.retornaData(dataInicial), ConverteData.retornaData(dataFinal));
			for (ContasReceber contasReceber : cr) {
				valorTotal += contasReceber.getValorPrevista();
				valorPago += contasReceber.getValorEfetivo();
				if(contasReceber.getStatus()==Status.ABERTO){
					valorAberto += contasReceber.getValorPrevista();
				}
			}
			
			final List<ContasReceberTO> to = new ContasReceberAssembler().entity2EntityTO(cr);
			for (ContasReceberTO contasReceberTO : to) {
				final Clientes cliente = new ClientesDAO().findById(contasReceberTO.getIdCliente());

				contasReceberTO.setIdParcela(contasReceberTO.getNumeroParcela());
				contasReceberTO.setStatus(Status.retornaTipo(Integer.parseInt(contasReceberTO.getStatus())));

				contasReceberTO.setIdCliente(cliente.getNome());
				if (contasReceberTO.getValorEfetivo().equals("0,00")){
					contasReceberTO.setDataEfetiva("");
				}
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			map.put("pago", ConverteNumero.converteNumero(valorPago));
			map.put("aberto", ConverteNumero.converteNumero(valorAberto));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/contasReceber.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);
	}
	
	public ActionForward relatorioContasReceberClienteSintetico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String idCliente = request.getParameter("idCliente");
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasReceberSintetico.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/contasReceber.pdf");

		double valorTotal = 0.0;
		double valorPago = 0.0;
		double valorAberto = 0.0;
		
		final List<ContasReceber> cr = RelatorioDAO.getInstance().relatorioContasReceberCliente(idCliente);
		for (ContasReceber contasReceber : cr) {
			valorTotal += contasReceber.getValorPrevista();
			valorPago += contasReceber.getValorEfetivo();
			if(contasReceber.getStatus()==Status.ABERTO){
				valorAberto += contasReceber.getValorPrevista();
			}
		}

		final List<ContasReceberTO> to = new ContasReceberAssembler().entity2EntityTO(cr);
		for (ContasReceberTO contasReceberTO : to) {
			final Clientes cliente = new ClientesDAO().findById(contasReceberTO.getIdCliente());

			contasReceberTO.setIdParcela(contasReceberTO.getNumeroParcela());
			contasReceberTO.setStatus(Status.retornaTipo(Integer.parseInt(contasReceberTO.getStatus())));

			contasReceberTO.setIdCliente(cliente.getNome());
			if (contasReceberTO.getValorEfetivo().equals("0,00")){
				contasReceberTO.setDataEfetiva("");
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("total", ConverteNumero.converteNumero(valorTotal));
		map.put("pago", ConverteNumero.converteNumero(valorPago));
		map.put("aberto", ConverteNumero.converteNumero(valorAberto));
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		saida = request.getContextPath()+("/relatorio/contasReceber.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);

	}

	
	public ActionForward relatorioContasReceberInadimplentes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataPrevista = request.getParameter("dataPrevista");
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/inadimplentes.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/inadimplentes.pdf");
		final GregorianCalendar calendar = new GregorianCalendar();
		
		
		double valorTotal = 0.0;
		final List<ContasReceber> cr = RelatorioDAO.getInstance().relatorioContasReceberInadimplentes(ConverteData.retornaData(dataPrevista));
		for(int i = cr.size(); i > 0; i--){
			System.out.println(ConverteData.retornaData(cr.get(i-1).getDataPrevista()));
			System.out.println(ConverteData.retornaData(calendar));
			if (calendar.before(cr.get(i-1).getDataPrevista())){
				cr.remove(i-1);
			}
		}
		for (ContasReceber contasReceber : cr) {
			valorTotal += contasReceber.getValorPrevista();
		}
		
		final List<ContasReceberTO> to = new ContasReceberAssembler().entity2EntityTO(cr);
		
		for (ContasReceberTO contasReceberTO : to) {
			final Clientes cliente = new ClientesDAO().findById(contasReceberTO.getIdCliente());
			contasReceberTO.setIdNotaFiscal(contasReceberTO.getNumeroNotaFiscal());
			contasReceberTO.setIdParcela(contasReceberTO.getNumeroParcela());
			contasReceberTO.setStatus(Status.retornaTipo(Integer.parseInt(contasReceberTO.getStatus())));
			
			contasReceberTO.setIdCliente(cliente.getNome());
			if (contasReceberTO.getValorEfetivo().equals("0,00")){
				contasReceberTO.setDataEfetiva("");
			}
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("imagem", imagemNutec());
		map.put("imagem1", imagemGoverno());
		map.put("inadimplente", ConverteNumero.converteNumero(valorTotal));
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
		try {
			JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
			JasperExportManager.exportReportToPdfFile(print, saida);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		saida = request.getContextPath()+("/relatorio/inadimplentes.pdf");
		request.setAttribute("saida", saida);
		return mapping.findForward(RELATORIO);
	}

	public ActionForward relatorioCaixa(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		final ActionMessages errors = new ActionMessages();
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		final String jasper = getServlet().getServletContext().getRealPath("/jasper/caixa.jasper");
		String saida = getServlet().getServletContext().getRealPath("/relatorio/caixa.pdf");
		
		try {
			ConverteData.retornaData(dataFinal);
			ConverteData.retornaData(dataInicial);
		} catch (Exception e) {
			errors.add("dataInvalida", new ActionMessage("data.invalida.erro"));
		}
		if (errors.isEmpty()) {
			
			final GregorianCalendar gcInicial = ConverteData.retornaData(dataInicial);
			final GregorianCalendar gcFinal = ConverteData.retornaData(dataFinal);
			final List<Caixa> caixaList = RelatorioDAO.getInstance().relatorioCaixaData(gcInicial, gcFinal);

			if (!caixaList.isEmpty()){

				double sa = 0.0;
				double sd = 0.0;
				double sat = 0.0;

				/*
				 * Caixas Anteriores
				 */
				List<Caixa> listCaixaAnterior = ((CaixaBO) CaixaBO.getInstance()).findAllAnterior(gcInicial);
				for (Caixa caixa : listCaixaAnterior) {
					sa += caixa.getValor();
				}

				/*
				 * Caixa do dia
				 */
				for (Caixa caixa : caixaList) {
					sd += caixa.getValor();

				}

				/*
				 * Saldo Total
				 */
				sat = sa + sd;
				String saldoAnterior = "";
				String saldoDia = "";
				String saldoAtual = "";
				try {
					saldoAnterior = ConverteNumero.converteNumero(sa);
				} catch (Exception e) {
					saldoAnterior = "0,00";
				}
				try {
					saldoDia = ConverteNumero.converteNumero(sd);
				} catch (Exception e) {
					saldoDia = "0,00";
				}
				try {
					saldoAtual = ConverteNumero.converteNumero(sat);
				} catch (Exception e) {
					saldoAtual = "0,00";
				}

				final List<CaixaTO> to = new CaixaAssembler().entity2EntityTO(caixaList);
				final Map<String, String> map = new HashMap<String, String>();

				map.put("imagem", imagemNutec());
				map.put("imagem1", imagemGoverno());
				map.put("saldoAnterior", saldoAnterior);
				map.put("saldoDia", saldoDia);
				map.put("saldoAtual", saldoAtual);

				try {
					JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
					JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
					JasperExportManager.exportReportToPdfFile(print, saida);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			saida = request.getContextPath()+("/relatorio/caixa.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);

		return prepareRelatorio(mapping, form, request, response);
	}
	
	public ActionForward relatorioContasPagarAnalitico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		final ActionMessages errors = new ActionMessages();
		
		try {
			ConverteData.retornaData(dataInicial);
			ConverteData.retornaData(dataFinal);
		} catch (Exception e) {
			errors.add("dataContaAnaliticoInvalida", new ActionMessage("data.invalida.erro"));
		}
		if (errors.isEmpty()){
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasPagarAnalitico.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/contasPagar.pdf");

			double valorTotal = 0.0;
			final List<ContasPagar> cp = RelatorioDAO.getInstance().relatorioContasPagar(ConverteData.retornaData(dataInicial), ConverteData.retornaData(dataFinal));
			for (ContasPagar contasPagar : cp) {
				contasPagar.setValorEfetivo(contasPagar.getValorEfetivo()*-1);
				valorTotal += contasPagar.getValorPrevista();
			}
			
			final List<ContasPagarTO> to = new ContasPagarAssembler().entity2EntityTO(cp);
			for (ContasPagarTO contasPagarTO : to) {
				final Divisao divisao = new DivisaoDAO().findById(Integer.parseInt(contasPagarTO.getIdDivisao()));

				if(!contasPagarTO.getIdNotaFiscal().equals("")){
					contasPagarTO.setIdParcela(contasPagarTO.getNumeroParcela());
				} else {
					contasPagarTO.setIdPedidoDespesa(contasPagarTO.getNumeroPD());
				}
				contasPagarTO.setIdDivisao(divisao.getNome());
				if (contasPagarTO.getValorEfetivo().equals("0,00")){
					contasPagarTO.setDataEfetiva("");
				}
				
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/contasPagar.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);
	}
	
	public ActionForward relatorioContasPagarSintetico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		final ActionMessages errors = new ActionMessages();
		
		try {
			ConverteData.retornaData(dataInicial);
			ConverteData.retornaData(dataFinal);
		} catch (Exception e) {
			errors.add("dataContaAnaliticoInvalida", new ActionMessage("data.invalida.erro"));
		}
		if (errors.isEmpty()){
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasPagarSintetico.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/contasPagar.pdf");
			
			double valorTotal = 0.0;
			double valorPago = 0.0;
			double valorAberto = 0.0;
			final List<ContasPagar> cp = RelatorioDAO.getInstance().relatorioContasPagar(ConverteData.retornaData(dataInicial), ConverteData.retornaData(dataFinal));
			for (ContasPagar contasPagar : cp) {
				if(contasPagar.getValorEfetivo()!= 0){
					contasPagar.setValorEfetivo(contasPagar.getValorEfetivo()*-1);
				}
				valorPago += contasPagar.getValorEfetivo();
				valorTotal += contasPagar.getValorPrevista();
				if(contasPagar.getStatus()==Status.ABERTO){
					valorAberto += contasPagar.getValorPrevista();
				}
				
			}
			
			final List<ContasPagarTO> to = new ContasPagarAssembler().entity2EntityTO(cp);
			for (ContasPagarTO contasPagarTO : to) {
				final Divisao divisao = new DivisaoDAO().findById(Integer.parseInt(contasPagarTO.getIdDivisao()));

				if(!contasPagarTO.getIdNotaFiscal().equals("")){
					contasPagarTO.setIdParcela(contasPagarTO.getNumeroParcela());
				} else {
					contasPagarTO.setIdPedidoDespesa(contasPagarTO.getNumeroPD());
				}
				contasPagarTO.setIdDivisao(divisao.getNome());
				if (contasPagarTO.getValorEfetivo().equals("0,00")){
					contasPagarTO.setDataEfetiva("");
				}
				
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			map.put("pago", ConverteNumero.converteNumero(valorPago));
			map.put("aberto", ConverteNumero.converteNumero(valorAberto));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/contasPagar.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);
	}
	
	public ActionForward relatorioContasPagarDivisaoSintetico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String idDivisao = request.getParameter("idDivisao");
		final ActionMessages errors = new ActionMessages();
		
		if (errors.isEmpty()){
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/contasPagarSintetico.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/contasPagar.pdf");
			
			double valorTotal = 0.0;
			double valorPago = 0.0;
			double valorAberto = 0.0;
			final List<ContasPagar> cp = RelatorioDAO.getInstance().relatorioContasPagarDivisao(Integer.parseInt(idDivisao));
			for (ContasPagar contasPagar : cp) {
				if(contasPagar.getValorEfetivo()!= 0){
					contasPagar.setValorEfetivo(contasPagar.getValorEfetivo()*-1);
				}
				valorPago += contasPagar.getValorEfetivo();
				valorTotal += contasPagar.getValorPrevista();
				if(contasPagar.getStatus()==Status.ABERTO){
					valorAberto += contasPagar.getValorPrevista();
				}
				
			}
			
			final List<ContasPagarTO> to = new ContasPagarAssembler().entity2EntityTO(cp);
			for (ContasPagarTO contasPagarTO : to) {
				final Divisao divisao = new DivisaoDAO().findById(Integer.parseInt(contasPagarTO.getIdDivisao()));

				if(!contasPagarTO.getIdNotaFiscal().equals("")){
					contasPagarTO.setIdParcela(contasPagarTO.getNumeroParcela());
				} else {
					contasPagarTO.setIdPedidoDespesa(contasPagarTO.getNumeroPD());
				}
				contasPagarTO.setIdDivisao(divisao.getNome());
				if (contasPagarTO.getValorEfetivo().equals("0,00")){
					contasPagarTO.setDataEfetiva("");
				}
				
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(valorTotal));
			map.put("pago", ConverteNumero.converteNumero(valorPago));
			map.put("aberto", ConverteNumero.converteNumero(valorAberto));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/contasPagar.pdf");
			request.setAttribute("saida", saida);
			return mapping.findForward(RELATORIO);
		} else { 
			request.setAttribute(LOAD_PAGINA, "/WEB-INF/pages/relatorio/geraRelatorio.jsp");
		}

		saveErrors(request, errors);
		return prepareRelatorio(mapping, form, request, response);
	}


	public ActionForward relatorioLaboratorio(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		/*
		 * Aqui é recuperado o id da nota fiscal, localizado a nota fiscal, localizado o nome do cliente e o tipo de nota fiscal
		 * depois é setado os parametros necessarios para que o relatório seja exibido corretamente e é enviado para a pagina padrão do relatório
		 */
		final String idLaboratorio = request.getParameter("idLaboratorio");
		final String dataInicial = request.getParameter("dataInicial");
		final String dataFinal = request.getParameter("dataFinal");
		
		
		final ActionMessages errors = new ActionMessages();
		try {
			final GregorianCalendar calendarInicial = ConverteData.retornaData(dataInicial);
			final GregorianCalendar calendarFinal = ConverteData.retornaData(dataFinal);
			
			final String jasper = getServlet().getServletContext().getRealPath("/jasper/laboratorio.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/laboratorio.pdf");

			final List<ItensNotaFiscal> inf = RelatorioDAO.getInstance().relatorioLaboratorio(Integer.parseInt(idLaboratorio), calendarInicial, calendarFinal);
			double total = 0;
			for (ItensNotaFiscal i :inf){
				total += i.getValorTotal();
			}
			final List<ItensNotaFiscalTO> to = new ItensNotaFiscalAssembler().entity2EntityTO(inf);
			
			//		criação dos parametros
			final Map<String, String> map = new HashMap<String, String>();
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("total", ConverteNumero.converteNumero(total));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/laboratorio.pdf");
			request.setAttribute("saida", saida);
			
		} catch (Exception e) {
			errors.add("dataInvalida", new ActionMessage("data.invalida.erro"));
			saveErrors(request, errors);
			return prepareRelatorio(mapping, form, request, response);
		}
		
		
		return mapping.findForward(RELATORIO);
	}
	
	public ActionForward relatorioPedidoDespesaAnalitico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final String idPD = request.getParameter("idPD");

		if(!GenericValidator.isBlankOrNull(idPD)){

			final List<PedidoDespesa> pd = RelatorioDAO.getInstance().relatorioPedidoDespesa(Integer.parseInt(idPD));

			final String jasper = getServlet().getServletContext().getRealPath("/jasper/PD.jasper");
			String saida = getServlet().getServletContext().getRealPath("/relatorio/PD.pdf");

			final List<PedidoDespesaTO> to = PedidoDespesaAssembler.getInstance().entity2EntityTO(pd);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subConnection", getSubConnection());
			map.put("imagem", imagemNutec());
			map.put("imagem1", imagemGoverno());
			map.put("SUBREPORT_DIR", subDir());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(to);
			try {
				JasperPrint print = JasperFillManager.fillReport(jasper,map,ds);
				JasperExportManager.exportReportToPdfFile(print, saida);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			saida = request.getContextPath()+("/relatorio/PD.pdf");
			request.setAttribute("saida", saida);
		}
		return mapping.findForward(RELATORIO);
	}


}
