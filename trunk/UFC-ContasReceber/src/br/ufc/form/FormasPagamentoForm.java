package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.FormasPagamentoTO;

public class FormasPagamentoForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1247884072667133656L;
	private FormasPagamentoTO theItem = new FormasPagamentoTO();
	private List<FormasPagamentoTO> items = new ArrayList<FormasPagamentoTO>();
	

	public FormasPagamentoTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new FormasPagamentoTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, FormasPagamentoTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new FormasPagamentoTO());
		}

		items.add(index, newItem);
	}

	public FormasPagamentoTO getTheItem() {
		return theItem;
	}

	public void setTheItem(FormasPagamentoTO theItem) {
		this.theItem = theItem;
	}

	public List<FormasPagamentoTO> getItems() {
		return items;
	}

	public void setItems(List<FormasPagamentoTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setId("");
		theItem.setFormaPagamento("");
		theItem.setChecked(false);
		
		for (FormasPagamentoTO formasPagamentoTO : items) {
			formasPagamentoTO.setId("");
			formasPagamentoTO.setFormaPagamento("");
			formasPagamentoTO.setChecked(false);
		}
	}

}
