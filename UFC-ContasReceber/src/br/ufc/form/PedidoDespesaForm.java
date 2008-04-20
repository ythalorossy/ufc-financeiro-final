package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.PedidoDespesaTO;


public class PedidoDespesaForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8938685251544514599L;
	private PedidoDespesaTO theItem = new PedidoDespesaTO();
	private List<PedidoDespesaTO> items = new ArrayList<PedidoDespesaTO>();

	public PedidoDespesaTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new PedidoDespesaTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, PedidoDespesaTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new PedidoDespesaTO());
		}

		items.add(index, newItem);
	}

	public PedidoDespesaTO getTheItem() {
		return theItem;
	}

	public void setTheItem(PedidoDespesaTO theItem) {
		this.theItem = theItem;
	}

	public List<PedidoDespesaTO> getItems() {
		return items;
	}

	public void setItems(List<PedidoDespesaTO> items) {
		this.items = items;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setChecked(false);
		theItem.setDataPD("");
		theItem.setFonteRecurso("");
		theItem.setId("");
		theItem.setIdDivisao("");
		theItem.setJustificativa("");
		theItem.setNomeDivisao("");
		theItem.setNumeroPD("");
		theItem.setOrcamento("");
		theItem.setStatus("");
		theItem.setTipoServico("");
		theItem.setValorPrevisto("");
		theItem.setValorCotado("");
		theItem.setProjeto("");
		theItem.setIdLaboratorio("");
		theItem.setNomeLaboratorio("");
		for (PedidoDespesaTO theItem : this.items) {
			theItem.setChecked(false);
			theItem.setDataPD("");
			theItem.setFonteRecurso("");
			theItem.setId("");
			theItem.setIdDivisao("");
			theItem.setJustificativa("");
			theItem.setNomeDivisao("");
			theItem.setNumeroPD("");
			theItem.setOrcamento("");
			theItem.setStatus("");
			theItem.setTipoServico("");
			theItem.setValorPrevisto("");
			theItem.setValorCotado("");
			theItem.setProjeto("");
			theItem.setIdLaboratorio("");
			theItem.setNomeLaboratorio("");
		}
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		return super.validate(mapping, request);
	}
}
