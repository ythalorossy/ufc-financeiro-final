package br.ufc.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.Caixa;
import br.com.ContasReceber;
import br.com.FormasPagamento;
import br.com.Observacao;
import br.ufc.BO.CaixaBO;
import br.ufc.BO.ContasReceberBO;
import br.ufc.BO.FormasPagamentoBO;
import br.ufc.TO.ContasReceberTO;
import br.ufc.TO.FormasPagamentoTO;
import br.ufc.assembler.ContasReceberAssembler;
import br.ufc.assembler.FormasPagamentoAssembler;
import br.ufc.form.ContasReceberForm;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

@SuppressWarnings("serial")
public class ContasReceberAction extends DispatchAction implements Serializable {
	
	private GregorianCalendar calendar = new GregorianCalendar();
	
	private static final String LOAD_PAGE = "loadPage";
	private static final String MONTAR_CAIXA = "/WEB-INF/pages/caixaContasReceber/caixaContasReceber.jsp";
	
	public ActionForward baixar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// Criação da Lista de erros
		final ActionMessages errors = new ActionMessages();
		// Recuperando informações do request
		final String jurosDesconto = request.getParameter("jurosDesconto");
		final String dataPagamento = request.getParameter("dataPagamento");
		// convertendo a data em um calendario
		final GregorianCalendar calendar = ConverteData.retornaData(dataPagamento);
		// buscando todos os caixas por este calendario 
		final List<Caixa> caixaList = ((CaixaBO)CaixaBO.getInstance()).findAllByDia(calendar);
		boolean resultado = true;
		
		if(dataPagamento.length() <10){
			errors.add("dataLenghtErro", new ActionMessage("data.lenght.erro"));
			resultado = false;
		}
		
		if (GregorianCalendar.getInstance().before(calendar)){
			errors.add("dataBaixaMaior", new ActionMessage("data.baixa.maior"));
			resultado = false;
		}

		// testando se existe alguma tupla do caixa com o status de batido, se houver alguma tupla é setado 
		// um valor true para a variavel boleana
		if (resultado)
		for (Caixa caixa : caixaList) {
			if (caixa.getStatus() == Status.BATIDO){
				resultado = false;
				errors.add("caixaBatido", new ActionMessage("CAIXA.BATIDO"));
				break;
			}
		}
		
		
		// Se o resultado for falso a conta poderá ser baixa pois, por medida de segurança não se pode baixar uma conta com o caixa tendo o status
		// Batido
		if (resultado){
			ContasReceberForm contasReceberForm = (ContasReceberForm) form;
			ContasReceberTO contasReceberTO = contasReceberForm.getTheItem();
			ContasReceber contasReceber = ContasReceberAssembler.getInstance().entityTO2Entity(contasReceberTO);

			//Verificação e Segurança para garantir que uma Conta só possa ser baixada uma unica vez
			ContasReceber contasReceber2Test = ContasReceberBO.getInstance().findById(contasReceber.getId());
			
			if(contasReceber2Test.getStatus()==Status.PAGO) {
				errors.add("erroSalvar", new ActionMessage("erro.conta.pago"));
			}
			if (errors.isEmpty()){
				if (!((ContasReceberBO)ContasReceberBO.getInstance()).baixarContasReceber(contasReceber, jurosDesconto, calendar)){
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
		
		ContasReceberForm crForm = (ContasReceberForm) form;
		List<ContasReceber> listAllContasReceber = new ArrayList<ContasReceber>();
		List<ContasReceberTO> listAllContasReceberTO = new ArrayList<ContasReceberTO>();
		
		/*
		 * procura por data no request.
		 * caso nao encontre, assume data atual.
		 */
		String dataToList = request.getParameter("dataToList");
		if ((dataToList != null) && !(dataToList.equals(""))) {
			
			calendar = ConverteData.retornaData(dataToList);
			listAllContasReceber = ((ContasReceberBO)ContasReceberBO.getInstance()).findAll(calendar);
			
		} else {

			listAllContasReceber = ((ContasReceberBO)ContasReceberBO.getInstance()).findAll();
			
		}

		listAllContasReceberTO = ContasReceberAssembler.getInstance().entity2EntityTO(listAllContasReceber);
		
		
		final List<FormasPagamento> listAllFormasPagamento = FormasPagamentoBO.getInstance().findAll();
		final List<FormasPagamentoTO> listAllFormasPagamentoTO = FormasPagamentoAssembler.getInstance().entity2EntityTO(listAllFormasPagamento);
		
		crForm.setItems(listAllContasReceberTO);
		request.setAttribute("listAllFormasPagamentoTO", listAllFormasPagamentoTO);
		//request.setAttribute("listAllContasReceber", listAllContasReceberTO);

		request.setAttribute(LOAD_PAGE, MONTAR_CAIXA);
		return mapping.findForward("index");
	}
	/**
	 * Método responsável por excluir uma observação de uma dada conta a receber
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteObservacao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Criação do form
		final ContasReceberForm crForm = (ContasReceberForm) form;
		// recuperação do id da observação que irá ser deletado
		final int idObservacao = Integer.parseInt(request.getParameter("observacaoId"));
		// Criação do objeto ContasReceber baseado no id que subiu com o formulário
		final ContasReceber cr = ContasReceberBO.getInstance().findById(Integer.parseInt(crForm.getTheItem().getId()));
		// Recuperando a lista de Observacao que existe dentro do objeto ContasReceber
		final List<Observacao> observacao = cr.getObservacao();
		//Iteração sobre a lista de observação para localizar a observação que deverá ser deletado
		for (int i = 0; i < observacao.size(); i++){
			System.out.println(observacao.get(i).getId());
			if (observacao.get(i).getId() == idObservacao){
				// Deleção do objeto Observacao referente ao id da observação recuperado pelo request
				((ContasReceberBO)ContasReceberBO.getInstance()).deleteObservacao(observacao.get(i));
			}
			
		}
				
		return montarCaixa(mapping, form, request, response);
	}
	
	/**
	 * Método que atualiza o contas a receber setando uma observação
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateObservacao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final String ob = request.getParameter("observacao");
		boolean result = true;
		if (GenericValidator.isBlankOrNull(ob)){
			result = false;
		}

		if(result){
			// Criação do formulário
			final ContasReceberForm crForm = (ContasReceberForm) form;
			final ActionMessages errors = new ActionMessages();
			final ContasReceber cr = ContasReceberBO.getInstance().findById(Integer.parseInt(crForm.getTheItem().getId()));
			// Criação do objeto Observacao recuperado do ContasReceber
			final List<Observacao> crObservacao = cr.getObservacao();



			// Preenchendo o objeto Observacao
			crObservacao.add(fillObservacaoBeam(request));
			cr.setObservacao(crObservacao);
			// Atualizando o ContasReceber
			if(!ContasReceberBO.getInstance().update(cr)){
				errors.add("",null);
			}
		}
		return montarCaixa(mapping, form, request, response);
	}
	
	/**
	 * Método que preenche um objeto Observação
	 * @param request
	 * @return
	 */
	public Observacao fillObservacaoBeam(HttpServletRequest request){
		// Recuperando o texto da Observação
		final String ob = request.getParameter("observacao");
		final Observacao observacao = new Observacao();
		// Setando a data atual da observação
		observacao.setData(new GregorianCalendar());
		// Setando a observação
		observacao.setObservacao(ob);
		
		return observacao;
	}
	
}
