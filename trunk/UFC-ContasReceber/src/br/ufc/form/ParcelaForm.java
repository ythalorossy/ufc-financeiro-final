package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.ParcelaTO;


public class ParcelaForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5990565104019043581L;
	private ParcelaTO theItem = new ParcelaTO();
	private List<ParcelaTO> items = new ArrayList<ParcelaTO>();
	

	public ParcelaTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new ParcelaTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, ParcelaTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new ParcelaTO());
		}

		items.add(index, newItem);
	}

	public ParcelaTO getTheItem() {
		return theItem;
	}

	public void setTheItem(ParcelaTO theItem) {
		this.theItem = theItem;
	}

	public List<ParcelaTO> getItems() {
		return items;
	}

	public void setItems(List<ParcelaTO> items) {
		this.items = items;
	}
	
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		theItem.setChecked(false);
		theItem.setDataPagamento("");
		theItem.setId("");
		theItem.setIdCliente("");
		theItem.setIdNotaFiscal("");
		theItem.setStatus("");
		theItem.setValor("");
		theItem.setNomeCliente("");
		theItem.setNumeroNF("");
		theItem.setNumeroParcela("");
		
		for (ParcelaTO theItem : this.items) {
			theItem.setChecked(false);
			theItem.setDataPagamento("");
			theItem.setId("");
			theItem.setIdCliente("");
			theItem.setIdNotaFiscal("");
			theItem.setStatus("");
			theItem.setValor("");
			theItem.setNumeroNF("");
			theItem.setNumeroParcela("");

		}
	}

}
