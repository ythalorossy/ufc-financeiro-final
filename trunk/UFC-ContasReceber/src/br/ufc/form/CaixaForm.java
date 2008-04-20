package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.CaixaTO;

public class CaixaForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1125118907894169059L;
	private CaixaTO theItem = new CaixaTO();
	private List<CaixaTO> items = new ArrayList<CaixaTO>();
	

	public CaixaTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new CaixaTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, CaixaTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new CaixaTO());
		}

		items.add(index, newItem);
	}

	public CaixaTO getTheItem() {
		return theItem;
	}

	public void setTheItem(CaixaTO theItem) {
		this.theItem = theItem;
	}

	public List<CaixaTO> getItems() {
		return items;
	}

	public void setItems(List<CaixaTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

}
