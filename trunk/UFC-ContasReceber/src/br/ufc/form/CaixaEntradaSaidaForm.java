package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.CaixaEntradaSaidaTO;

public class CaixaEntradaSaidaForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5239073486523849360L;
	private CaixaEntradaSaidaTO theItem = new CaixaEntradaSaidaTO();
	private List<CaixaEntradaSaidaTO> items = new ArrayList<CaixaEntradaSaidaTO>();
	

	public CaixaEntradaSaidaTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new CaixaEntradaSaidaTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, CaixaEntradaSaidaTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new CaixaEntradaSaidaTO());
		}

		items.add(index, newItem);
	}

	public CaixaEntradaSaidaTO getTheItem() {
		return theItem;
	}

	public void setTheItem(CaixaEntradaSaidaTO theItem) {
		this.theItem = theItem;
	}

	public List<CaixaEntradaSaidaTO> getItems() {
		return items;
	}

	public void setItems(List<CaixaEntradaSaidaTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.theItem = new CaixaEntradaSaidaTO();
		this.items = new ArrayList<CaixaEntradaSaidaTO>();
		
	}

}
