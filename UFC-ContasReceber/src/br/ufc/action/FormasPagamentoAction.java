package br.ufc.action;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.FormasPagamento;
import br.ufc.BO.FormasPagamentoBO;
import br.ufc.TO.FormasPagamentoTO;
import br.ufc.assembler.FormasPagamentoAssembler;
import br.ufc.form.FormasPagamentoForm;

@SuppressWarnings("serial")
public class FormasPagamentoAction extends DispatchAction implements
		Serializable {

	private static final String LOAD_PAGE = "loadPage";
	
	private static final String PREPARE_SAVE = "/WEB-INF/pages/formasPagamento/formasPagamento.jsp";
	private static final String PREPARE_UPDATE = "/WEB-INF/pages/formasPagamento/formasPagamentoPrepareUpdate.jsp";


	/**
	 * PREPARE SAVE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<FormasPagamentoTO> listAllFormasPagamentoTO = FormasPagamentoAssembler.getInstance().entity2EntityTO(FormasPagamentoBO.getInstance().findAll());
		
		request.setAttribute("listAllFormasPagamentoTO", listAllFormasPagamentoTO);

		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		
		return mapping.findForward("index");
	}

	/**
	 * SAVE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		
		FormasPagamentoForm formasPagamentoForm = (FormasPagamentoForm) form;
		FormasPagamentoTO formasPagamentoTO = formasPagamentoForm.getTheItem();
		FormasPagamento formasPagamento = new FormasPagamentoAssembler().entityTO2Entity(formasPagamentoTO);
		errors = valida(formasPagamentoForm, request);
		if(errors.isEmpty()){
			/*
			 * Ao Salvar se retorna: - False: adiciona error e muda a pagina para
			 * PrepareSave Novamente.
			 */
			if (!new FormasPagamentoBO().save(formasPagamento)) {
				errors.add("save", new ActionMessage("SAVE.ERROR"));
			} else {
				errors.add("save", new ActionMessage("SAVE.SUCCESS"));
			}
		}

		/*
		 * Salva lista de errors
		 */
		saveErrors(request, errors);

		/*
		 * limpa dados do form
		 */
		form.reset(mapping, request);
		
		return prepareSave(mapping, form, request, response);
	}

	

	/**
	 * PREPARE UPDATE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final FormasPagamentoForm pagamentoForm = (FormasPagamentoForm) form;
		
		int id = Integer.parseInt(pagamentoForm.getTheItem().getId());
		
		/*
		 * Recupera uma Forma de pagamento e converte-o para um TO
		 */
		FormasPagamento formasPagamento = new FormasPagamentoBO().findById(id);
		FormasPagamentoTO formasPagamentoTO = new FormasPagamentoAssembler()
				.entity2EntityTO(formasPagamento);

		/*
		 * Seta forma de pagamento TO no request e dispara para a pagina de update
		 */
		request.setAttribute("formasPagamentoTO", formasPagamentoTO);
		request.setAttribute(LOAD_PAGE, PREPARE_UPDATE);

		return mapping.findForward("index");
	}

	/**
	 * UPDATE
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		
		/*
		 * Converte o forma de pagamento que veio do Form em um Entity
		 */
		FormasPagamentoForm formasPagamentoForm = (FormasPagamentoForm) form;
		FormasPagamentoTO formasPagamentoTO = formasPagamentoForm.getTheItem();
		FormasPagamento formasPagamento = new FormasPagamentoAssembler()
				.entityTO2Entity(formasPagamentoTO);

		/*
		 * Realiza o update - return(true/false)
		 */
		if (new FormasPagamentoBO().update(formasPagamento)) {
			errors.add("update", new ActionMessage("UPDATE.SUCCESS"));
		} else {
			errors.add("update", new ActionMessage("UPDATE.ERROR"));
		}

		/*
		 * Salva lista de possíveis erros
		 */
		saveErrors(request, errors);

		/*
		 * Limpa o form
		 */
		form.reset(mapping, request);
		
		/*
		 * Apos atualizar as informacoes sempre voltar para o prepareSave
		 */
		return prepareSave(mapping, form, request, response);

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		FormasPagamentoForm formasPagamentoForm = (FormasPagamentoForm) form;
		List<FormasPagamento> listAllToDelete = new ArrayList<FormasPagamento>();
		List<FormasPagamentoTO> listAll = formasPagamentoForm.getItems();

		/*
		 * Interage na lista de TO's que veio da VIEW e sempre
		 * que achar um com checked true, coloca-o em uma lista
		 * para ser deletado.
		 */
		for (FormasPagamentoTO fpTO : listAll) {
			if (fpTO.isChecked()) {
				listAllToDelete.add(new FormasPagamentoAssembler()
						.entityTO2Entity(fpTO));
			}
		}

		/*
		 * Verifica se a lista de TO's para deletar é vazia
		 */
		if (!listAllToDelete.isEmpty()) {
			FormasPagamentoBO.getInstance().delete(listAllToDelete);
		}

		/*
		 * Limpa o form
		 */
		form.reset(mapping, request);
		
		return prepareSave(mapping, form, request, response);
	}
	
	private ActionMessages valida(FormasPagamentoForm form, HttpServletRequest request) {
		final FormasPagamentoTO to = form.getTheItem();
		final ActionMessages errors = new ActionMessages();
		if (GenericValidator.isBlankOrNull(to.getFormaPagamento())){
			errors.add("formaPagamento", new ActionMessage("formaPagamento.empty"));
		}
		saveErrors(request, errors);
		return errors;
	}

}
