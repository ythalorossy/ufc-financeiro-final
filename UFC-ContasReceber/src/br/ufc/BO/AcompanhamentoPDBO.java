package br.ufc.BO;

import java.util.List;

import br.com.AcompanhamentoPD;
import br.com.PedidoDespesa;
import br.ufc.DAO.AcompanhamentoPdDAO;


public class AcompanhamentoPDBO {

	public static AcompanhamentoPDBO getInstance() {
		return new AcompanhamentoPDBO();
	}

	public List<AcompanhamentoPD> findByPD(PedidoDespesa pedidoDespesa) {
		return new AcompanhamentoPdDAO().findByPD(pedidoDespesa);
	}

	public boolean save(AcompanhamentoPD apd) {
		return new AcompanhamentoPdDAO().save(apd);
	}

}
