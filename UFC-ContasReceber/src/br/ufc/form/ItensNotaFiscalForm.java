package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.ItensNotaFiscalTO;

public class ItensNotaFiscalForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3376724127121915282L;
	private ItensNotaFiscalTO theItem = new ItensNotaFiscalTO();
	private List<ItensNotaFiscalTO> items = new ArrayList<ItensNotaFiscalTO>();
	

	public ItensNotaFiscalTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new ItensNotaFiscalTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, ItensNotaFiscalTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new ItensNotaFiscalTO());
		}

		items.add(index, newItem);
	}

	public ItensNotaFiscalTO getTheItem() {
		return theItem;
	}

	public void setTheItem(ItensNotaFiscalTO theItem) {
		this.theItem = theItem;
	}

	public List<ItensNotaFiscalTO> getItems() {
		return items;
	}

	public void setItems(List<ItensNotaFiscalTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setChecked(false);
		theItem.setId("");
		theItem.setIdCliente("");
		theItem.setIdDivisao("");
		theItem.setIdNotaFiscal("");
		theItem.setQuantidade("");
		theItem.setServico("");
		theItem.setValor("");
		theItem.setValorTotal("");
		theItem.setStatus("");
		theItem.setNomeCliente("");
		theItem.setNumeroNF("");
		theItem.setNomeDivisao("");
		
		for (ItensNotaFiscalTO theItem: this.items) {
			theItem.setChecked(false);
			theItem.setId("");
			theItem.setIdCliente("");
			theItem.setIdDivisao("");
			theItem.setIdNotaFiscal("");
			theItem.setQuantidade("");
			theItem.setServico("");
			theItem.setValor("");
			theItem.setValorTotal("");
			theItem.setStatus("");
			theItem.setNomeCliente("");
			theItem.setNumeroNF("");
			theItem.setNomeDivisao("");
			
		}
	}
	

}
