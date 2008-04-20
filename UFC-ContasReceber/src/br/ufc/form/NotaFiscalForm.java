package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.NotaFiscalTO;


public class NotaFiscalForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1542760420877323739L;
	private NotaFiscalTO theItem = new NotaFiscalTO();
	private List<NotaFiscalTO> items = new ArrayList<NotaFiscalTO>();
	

	public NotaFiscalTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new NotaFiscalTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, NotaFiscalTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new NotaFiscalTO());
		}

		items.add(index, newItem);
	}

	public NotaFiscalTO getTheItem() {
		return theItem;
	}

	public void setTheItem(NotaFiscalTO theItem) {
		this.theItem = theItem;
	}

	public List<NotaFiscalTO> getItems() {
		return items;
	}

	public void setItems(List<NotaFiscalTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setChecked(false);
		theItem.setDataSaida("");
		theItem.setId("");
		theItem.setIdCliente("");
		theItem.setNotaFiscal("");
		theItem.setStatus("");
		theItem.setTipoNota("");
		theItem.setValorNota("");
		theItem.setNumeroProcesso("");
		theItem.setNomeCliente("");
		theItem.setNumeroContrato("");

		for (NotaFiscalTO theItem : this.items) {
			theItem.setChecked(false);
			theItem.setDataSaida("");
			theItem.setId("");
			theItem.setIdCliente("");
			theItem.setNotaFiscal("");
			theItem.setStatus("");
			theItem.setTipoNota("");
			theItem.setValorNota("");
			theItem.setNumeroProcesso("");
			theItem.setNomeCliente("");
			theItem.setNumeroContrato("");
		}
	}

}
