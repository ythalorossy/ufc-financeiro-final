package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.FormasPagamento;
import br.ufc.TO.FormasPagamentoTO;

public class FormasPagamentoAssembler implements
		ASSEMBLER<FormasPagamento, FormasPagamentoTO> {

	public static ASSEMBLER<FormasPagamento, FormasPagamentoTO> getInstance() {
		return new FormasPagamentoAssembler();
	}
	
	
	public FormasPagamentoTO entity2EntityTO(FormasPagamento entity) {
		final FormasPagamentoTO to = new FormasPagamentoTO();
		to.setId(String.valueOf(entity.getId()));
		to.setFormaPagamento(entity.getFormaPagamento());

		return to;

	}

	public List<FormasPagamentoTO> entity2EntityTO(List<FormasPagamento> entity) {
		final List<FormasPagamentoTO> listTO = new ArrayList<FormasPagamentoTO>();
		for (FormasPagamento formasPagamento : entity) {
			listTO.add(entity2EntityTO(formasPagamento));
		}
		return listTO;

	}

	public FormasPagamento entityTO2Entity(FormasPagamentoTO to) {
		final FormasPagamento entity = new FormasPagamento();
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}
		entity.setFormaPagamento(to.getFormaPagamento());

		return entity;

	}

	public List<FormasPagamento> entityTO2Entity(List<FormasPagamentoTO> to) {
		final List<FormasPagamento> listEntity = new ArrayList<FormasPagamento>();
		for (FormasPagamentoTO formasPagamentoTO : to) {
			listEntity.add(entityTO2Entity(formasPagamentoTO));
		}
		return listEntity;

	}

}
