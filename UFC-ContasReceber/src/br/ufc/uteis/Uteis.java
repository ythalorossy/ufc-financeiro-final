package br.ufc.uteis;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.ConverteNumero.ConverteNumero;
import br.ufc.form.CaixaEntradaSaidaForm;

import com.converte.ConverteData;

public class Uteis extends DispatchAction {

	private ActionMessages errors = new ActionMessages();

	public static Uteis getInstance() {
		return new Uteis();
	}

	public ActionMessages validateCES(CaixaEntradaSaidaForm caixaEntradaSaidaForm,HttpServletRequest request) {

		/*
		 * Valida o Valor
		 */
		try {
			ConverteNumero.converteNumero(caixaEntradaSaidaForm.getTheItem().getValor());
		} catch (Exception ex) {
			errors.add("valor", new ActionMessage(""));
		}

		/*
		 * Valida Data
		 */
		try {
			ConverteData.retornaData(caixaEntradaSaidaForm.getTheItem()
					.getDataTransacao());
		} catch (Exception ex) {
			errors.add("data", new ActionMessage(""));
		}

		/*
		 * Operacao
		 */
		if (GenericValidator.isBlankOrNull(caixaEntradaSaidaForm.getTheItem()
				.getOperacao())) {
			errors.add("operacao", new ActionMessage(""));
		}

		/*
		 * Descricao
		 */
		if (GenericValidator.isBlankOrNull(caixaEntradaSaidaForm.getTheItem()
				.getDescricao())) {
			errors.add("descricao", new ActionMessage(""));
		}

		saveErrors(request, errors);

		return errors;
	}
}
