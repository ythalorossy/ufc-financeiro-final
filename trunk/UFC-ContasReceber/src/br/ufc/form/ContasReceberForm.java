package br.ufc.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.ufc.TO.ContasReceberTO;

public class ContasReceberForm extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 273636591520271293L;
	private ContasReceberTO theItem = new ContasReceberTO();
	private List<ContasReceberTO> items = new ArrayList<ContasReceberTO>();
	

	public ContasReceberTO getItem(int index) {
		int size = items.size();

		if (index >= size) {
			for (int i = size; i < (index + 1); i++) {
				items.add(new ContasReceberTO());
			}
		}

		return items.get(index);
	}

	public void setItem(int index, ContasReceberTO newItem) {
		int size = items.size();

		if (index >= size) {
			items.add(new ContasReceberTO());
		}

		items.add(index, newItem);
	}

	public ContasReceberTO getTheItem() {
		return theItem;
	}

	public void setTheItem(ContasReceberTO theItem) {
		this.theItem = theItem;
	}

	public List<ContasReceberTO> getItems() {
		return items;
	}

	public void setItems(List<ContasReceberTO> items) {
		this.items = items;
	}
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		theItem.setChecked(false);
		theItem.setDataEfetiva("");
		theItem.setDataPrevista("");
		theItem.setDiasAtraso("");
		theItem.setId("");
		theItem.setIdCliente("");
		theItem.setIdFormasPagamento("");
		theItem.setIdNotaFiscal("");
		theItem.setIdParcela("");
		theItem.setNomeCliente("");
		theItem.setNumeroParcela("");
		theItem.setStatus("");
		theItem.setValorEfetivo("");
		theItem.setValorMonetario("");
		theItem.setValorPrevista("");
		
		
	}

}
