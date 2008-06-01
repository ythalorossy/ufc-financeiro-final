package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.CaixaEntradaSaida;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.TO.CaixaEntradaSaidaTO;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

public class CaixaEntradaSaidaAssembler implements ASSEMBLER<CaixaEntradaSaida, CaixaEntradaSaidaTO> {

	public static CaixaEntradaSaidaAssembler getInstance() {
		return new CaixaEntradaSaidaAssembler();
	}

	public CaixaEntradaSaidaTO entity2EntityTO(CaixaEntradaSaida entity) {
		final CaixaEntradaSaidaTO to = new CaixaEntradaSaidaTO();
		to.setDataTransacao(ConverteData.retornaData(entity.getDataTransacao()));
		to.setDescricao(entity.getDescricao());
		to.setId(String.valueOf(entity.getId()));
		to.setOperacao(String.valueOf(entity.getOperacao()));
		to.setValor(ConverteNumero.converteNumero(entity.getValor()));
		return to;

	}

	public List<CaixaEntradaSaidaTO> entity2EntityTO(List<CaixaEntradaSaida> entity) {
		final List<CaixaEntradaSaidaTO> listTO = new ArrayList<CaixaEntradaSaidaTO>();
		for (CaixaEntradaSaida caixaEntradaSaida : entity) {
			listTO
					.add(entity2EntityTO(caixaEntradaSaida));
		}
		return listTO;

	}

	public CaixaEntradaSaida entityTO2Entity(CaixaEntradaSaidaTO to) {
		final CaixaEntradaSaida entity = new CaixaEntradaSaida();
		try {
			entity.setDataTransacao(ConverteData.retornaData(to.getDataTransacao()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		entity.setDescricao(to.getDescricao());
		try {
			
			if (to.getOperacao().equals("ENTRADA")) {
				entity.setOperacao(Status.ENTRADA);
			}
			if (to.getOperacao().equals("SAIDA")) {
				entity.setOperacao(Status.SAIDA);
			}
			
		} catch (Exception e) {
		}
		try {
			entity.setValor(ConverteNumero.converteNumero(to.getValor()));
		} catch (Exception e) {
		}
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
			entity.setId(0);
		}
		return entity;
	}

	public List<CaixaEntradaSaida> entityTO2Entity(List<CaixaEntradaSaidaTO> to) {
		final List<CaixaEntradaSaida> listEntity = new ArrayList<CaixaEntradaSaida>();
		for (CaixaEntradaSaidaTO caixaEntradaSaidaTO : to) {
			listEntity.add(entityTO2Entity(caixaEntradaSaidaTO));
		}
		return listEntity;
	}

}