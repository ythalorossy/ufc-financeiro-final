package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.ItensPedidoDespesaTO;

public class ItensPedidoDespesaForm extends ActionForm {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2724826656582783862L;
	private ItensPedidoDespesaTO theItem = new ItensPedidoDespesaTO();
	private List<ItensPedidoDespesaTO> items = new ArrayList<ItensPedidoDespesaTO>();
	

	public ItensPedidoDespesaTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new ItensPedidoDespesaTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, ItensPedidoDespesaTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new ItensPedidoDespesaTO());
		}

		items.add(index, newItem);
	}

	public ItensPedidoDespesaTO getTheItem() {
		return theItem;
	}

	public void setTheItem(ItensPedidoDespesaTO theItem) {
		this.theItem = theItem;
	}

	public List<ItensPedidoDespesaTO> getItems() {
		return items;
	}

	public void setItems(List<ItensPedidoDespesaTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setChecked(false);
		theItem.setId("");
		theItem.setIdDivisao("");
		theItem.setIdPedidoDespesa("");
		theItem.setItem("");
		theItem.setNomeDivisao("");
		theItem.setQuantidade("");
		theItem.setStatus("");
		theItem.setUnidade("");
		theItem.setValorTotal("");
		theItem.setValorTotalCotado("");
		theItem.setValorUnitario("");
		theItem.setValorUnitarioCotado("");
		
		for (ItensPedidoDespesaTO theItem : this.items) {
			theItem.setChecked(false);
			theItem.setId("");
			theItem.setIdDivisao("");
			theItem.setIdPedidoDespesa("");
			theItem.setItem("");
			theItem.setNomeDivisao("");
			theItem.setQuantidade("");
			theItem.setStatus("");
			theItem.setUnidade("");
			theItem.setValorTotal("");
			theItem.setValorTotalCotado("");
			theItem.setValorUnitario("");
			theItem.setValorUnitarioCotado("");
		}
		
	}
}
