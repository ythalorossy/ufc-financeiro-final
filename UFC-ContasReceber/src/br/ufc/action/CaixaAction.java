package br.ufc.action;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.Caixa;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.CaixaBO;
import br.ufc.TO.CaixaTO;
import br.ufc.assembler.CaixaAssembler;
import br.ufc.form.CaixaForm;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

@SuppressWarnings("serial")
public class CaixaAction extends DispatchAction implements Serializable {

	private final String LOAD_PAGE = "loadPage";
	private final String MONTAR_CAIXA = "/WEB-INF/pages/caixa/caixa.jsp";
	
	
	
	/**
	 * Monta lista de Notas fiscais do dia, baseado por data passada
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward montarCaixa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final ActionMessages errors = new ActionMessages();
		GregorianCalendar gc = new GregorianCalendar();
		
		boolean caixaBatido = false;

		/*
		 * procura por data no request. caso nao encontre, assume data atual.
		 */

		String dataToList = (String) (request.getParameter("dataToList")==null ? request.getAttribute("dataToList"):request.getParameter("dataToList"));
		if (dataToList != null && !(dataToList.equals(""))) {
			gc = ConverteData.retornaData(dataToList);

		} else {
			gc = new GregorianCalendar();
		}

		double saldoAnterior = 0.0;
		double saldoDia = 0.0;
		double saldoAtual = 0.0;

		/*
		 * Caixas Anteriores
		 */
		List<Caixa> listCaixaAnterior = ((CaixaBO) CaixaBO.getInstance()).findAllAnterior(gc);
		for (Caixa caixa : listCaixaAnterior) {
			saldoAnterior += caixa.getValor();
		}

		/*
		 * Caixa do dia
		 */
		List<Caixa> listCaixaDia = ((CaixaBO) CaixaBO.getInstance()).findAllByDia(gc);
		for (Caixa caixa : listCaixaDia) {
			saldoDia += caixa.getValor();
			caixaBatido = (caixa.getStatus() == Status.BATIDO) ? true : false;
		}

		if (caixaBatido) {
			errors.add("caixa", new ActionMessage("CAIXA.BATIDO"));
			saveErrors(request, errors);
		}

		/*
		 * Saldo Total
		 */
		saldoAtual = saldoAnterior + saldoDia;

		List<CaixaTO> listCaixaDiaTO = CaixaAssembler.getInstance()
				.entity2EntityTO(listCaixaDia);

		request.setAttribute("dataAtual", ConverteData.retornaData(gc));
		request.setAttribute("saldoAnterior", ConverteNumero.converteNumero(saldoAnterior));
		request.setAttribute("saldoDia", ConverteNumero.converteNumero(saldoDia));
		request.setAttribute("saldoAtual", ConverteNumero.converteNumero(saldoAtual));
		request.setAttribute("listCaixaDiaTO", listCaixaDiaTO);

		
		
		request.setAttribute(LOAD_PAGE, MONTAR_CAIXA);
		return mapping.findForward("index");
	}

	/**
	 * Fechamento do dia - Muda o status de todas as Contas do caixa de um
	 * determinado dia de aguardando para batido
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward baterCaixa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Criação da lista de errors
		final ActionMessages errors = new ActionMessages();
		// Recuperando a data do caixa
		final GregorianCalendar calendar = ConverteData.retornaData(((CaixaForm) form).getTheItem().getDataPagamento());
		// Comparado se a data do caixa é maior que a data atual. Caso seja é lançado um erro pois é impossivel finalizar algo que esteja no futuro 
		if (new GregorianCalendar().getTime().before(calendar.getTime())){
			errors.add("impossivelBaterCaixa", new ActionMessage("data.baixa.maior.erro"));
		}
		// Caso a lista esteja vazia o caixa pode ser fechado
		if (errors.isEmpty()){
			if (((CaixaBO)CaixaBO.getInstance()).baterCaixa(calendar)) {
				errors.add("caixa", new ActionMessage("CAIXA.BATIDO"));
			}
		}

		saveErrors(request, errors);
		final String data = ConverteData.retornaData(calendar);
		request.setAttribute("dataToList", data);

		return montarCaixa(mapping, form, request, response);
	}

	/**
	 * Reabrir Caixa: Mudara o status de todos os caixas para Aguardando,
	 * de uma determinada data
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reabrirCaixa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final CaixaForm caixaForm = (CaixaForm) form;
		final String dataAtual = caixaForm.getTheItem().getDataPagamento(); 
		final GregorianCalendar calendar = ConverteData.retornaData(dataAtual);

		if (((CaixaBO) CaixaBO.getInstance()).reabrirCaixa(calendar)) {
			System.out.println("REABERTO");
		}
		request.setAttribute("dataToList", dataAtual);
		return montarCaixa(mapping, form, request, response);
	}

	/**
	 * Extorno de Caixa
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward extornarCaixa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String dataAtual = request.getParameter("dataAtual");
		final ActionMessages errors = new ActionMessages();
		final CaixaTO caixaTO = ((CaixaForm) form).getTheItem();
		final Caixa caixa = CaixaBO.getInstance().findById(Integer.parseInt(caixaTO.getId()));

		if (!((CaixaBO) CaixaBO.getInstance()).extornarCaixa(caixa)) {
			errors.add("",null);
		}
		request.setAttribute("dataToList", dataAtual);
		saveErrors(request, errors);
		return montarCaixa(mapping, form, request, response);
	}

}
