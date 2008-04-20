package br.ufc.action;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.CaixaEntradaSaida;
import br.ufc.BO.CaixaEntradaSaidaBO;
import br.ufc.TO.CaixaEntradaSaidaTO;
import br.ufc.assembler.CaixaEntradaSaidaAssembler;
import br.ufc.form.CaixaEntradaSaidaForm;
import br.ufc.uteis.Uteis;

@SuppressWarnings("serial")
public class CaixaEntradaSaidaAction extends DispatchAction implements
		Serializable {

	private static final String LOAD_PAGE = "loadPage";
	private static final String FORM_INSERT = "/WEB-INF/pages/caixaEntradaSaida/formEntradaSaida.jsp";
	
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		form.reset(mapping, request);
		
		request.setAttribute("operacao", "save");
		request.setAttribute(LOAD_PAGE, FORM_INSERT);
		
		return mapping.findForward("index");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		final ActionMessages errors = new ActionMessages();
		
		final CaixaEntradaSaidaForm caixaEntradaSaidaForm = (CaixaEntradaSaidaForm) form;
		final CaixaEntradaSaidaTO caixaEntradaSaidaTO = caixaEntradaSaidaForm.getTheItem();
		final CaixaEntradaSaida caixaEntradaSaida = CaixaEntradaSaidaAssembler.getInstance().entityTO2Entity(caixaEntradaSaidaTO);

		/*
		 * Verifica se ocorreu erro na validacão
		 */
		if (Uteis.getInstance().validateCES(caixaEntradaSaidaForm, request).isEmpty()) {
			if (!CaixaEntradaSaidaBO.getInstance().save(caixaEntradaSaida)) {
				errors.add("save", new ActionMessage("SAVE.ERROR"));
			}
		}
		
		saveErrors(request, errors);
		/*
		 * Errors de validacao ou Save,
		 * Retorna para o Formulario de Insercao
		 */
		if (errors.isEmpty()) {
			request.setAttribute(LOAD_PAGE, "PAGINA QUE SERA CARREGADA");
		}
		
		return prepareSave(mapping, form, request, response);
	}

}
