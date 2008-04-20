package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.AcompanhamentoPDTO;

public class AcompanhamentoPDForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8615187503603644134L;
	
	private AcompanhamentoPDTO theItem = new AcompanhamentoPDTO();
	private List<AcompanhamentoPDTO> items = new ArrayList<AcompanhamentoPDTO>();
	

	public AcompanhamentoPDTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new AcompanhamentoPDTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, AcompanhamentoPDTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new AcompanhamentoPDTO());
		}

		items.add(index, newItem);
	}

	public AcompanhamentoPDTO getTheItem() {
		return theItem;
	}

	public void setTheItem(AcompanhamentoPDTO theItem) {
		this.theItem = theItem;
	}

	public List<AcompanhamentoPDTO> getItems() {
		return items;
	}

	public void setItems(List<AcompanhamentoPDTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}



}
