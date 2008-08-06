package br.ufc.action;

import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.ContasPagar;
import br.com.FormasPagamento;
import br.ufc.BO.CaixaBO;
import br.ufc.BO.ContasPagarBO;
import br.ufc.BO.FormasPagamentoBO;
import br.ufc.TO.ContasPagarTO;
import br.ufc.TO.FormasPagamentoTO;
import br.ufc.assembler.ContasPagarAssembler;
import br.ufc.assembler.FormasPagamentoAssembler;
import br.ufc.form.ContasPagarForm;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

@SuppressWarnings("serial")
public class ContasPagarAction extends DispatchAction implements Serializable {
	
	private GregorianCalendar calendar = new GregorianCalendar();
	
	private static final String LOAD_PAGE = "loadPage";
	private static final String MONTAR_CAIXA = "/WEB-INF/pages/caixaContasPagar/caixaContasPagar.jsp";
	
	public ActionForward baixar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final String jurosDesconto = request.getParameter("jurosDesconto");
		final String dataPagamento = request.getParameter("dataPagamento");
		final ActionMessages errors = new ActionMessages();
		boolean result = true;
		ContasPagarForm contasReceberForm = (ContasPagarForm) form;
		ContasPagarTO contasPagarTO = contasReceberForm.getTheItem();
		ContasPagar contasPagar = ContasPagarAssembler.getInstance().entityTO2Entity(contasPagarTO);
		final List<Caixa> listCaixa = ((CaixaBO)CaixaBO.getInstance()).findAllByDia(ConverteData.retornaData(dataPagamento));
		
		final GregorianCalendar calendar = ConverteData.retornaData(dataPagamento);
		
		if (GregorianCalendar.getInstance().before(calendar)){
			errors.add("dataBaixaMaior", new ActionMessage("data.baixa.maior"));
			result = false;
		}
		
		if (result)
		for (Caixa caixa : listCaixa) {
			if (caixa.getStatus() == Status.BATIDO){
				result = false;
				errors.add("caixaBatido", new ActionMessage("CAIXA.BATIDO"));
				break;
			}
		}
		
		if(result){
			ContasPagar contasPagar2Test = ContasPagarBO.getInstance().findById(contasPagar.getId());
			if (contasPagar2Test.getStatus()==Status.PAGO){
				errors.add("erroSalvar", new ActionMessage("erro.conta.pago"));
			}
			if (errors.isEmpty()){
				if (!((ContasPagarBO)ContasPagarBO.getInstance()).baixarContasPagar(contasPagar, jurosDesconto, calendar)){
					errors.add("erroSalvar", new ActionMessage("erro.salvar"));
				}
			}
		} 
		
		
		saveErrors(request, errors);
		return montarCaixa(mapping, form, request, response);
	}
	
	public ActionForward montarCaixa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<ContasPagar> listAllContasPagar = new ArrayList<ContasPagar>();
		List<ContasPagarTO> listAllContasPagarTO = new ArrayList<ContasPagarTO>();
		
		/*
		 * procura por data no request.
		 * caso nao encontre, assume data atual.
		 */
		String dataToList = request.getParameter("dataToList");
		if ((dataToList != null) && !(dataToList.equals(""))) {
			
			calendar = ConverteData.retornaData(dataToList);
			listAllContasPagar = ((ContasPagarBO)ContasPagarBO.getInstance()).findAll(calendar);
			
		} else {

			listAllContasPagar = ((ContasPagarBO)ContasPagarBO.getInstance()).findAll();
			
		}

		listAllContasPagarTO = ContasPagarAssembler.getInstance().entity2EntityTO(listAllContasPagar);
		
		
		List<FormasPagamento> listAllFormasPagamento = FormasPagamentoBO.getInstance().findAll();
		List<FormasPagamentoTO> listAllFormasPagamentoTO = FormasPagamentoAssembler.getInstance().entity2EntityTO(listAllFormasPagamento);
		
		request.setAttribute("listAllFormasPagamentoTO", listAllFormasPagamentoTO);
		request.setAttribute("listAllContasPagar", listAllContasPagarTO);

		request.setAttribute(LOAD_PAGE, MONTAR_CAIXA);
		
		return mapping.findForward("index");
	}
	
}
