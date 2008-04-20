package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.ContasPagarTO;

public class ContasPagarForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6639403823513231866L;
	private ContasPagarTO theItem = new ContasPagarTO();
	private List<ContasPagarTO> items = new ArrayList<ContasPagarTO>();
	

	public ContasPagarTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new ContasPagarTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, ContasPagarTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new ContasPagarTO());
		}

		items.add(index, newItem);
	}

	public ContasPagarTO getTheItem() {
		return theItem;
	}

	public void setTheItem(ContasPagarTO theItem) {
		this.theItem = theItem;
	}

	public List<ContasPagarTO> getItems() {
		return items;
	}

	public void setItems(List<ContasPagarTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

}
