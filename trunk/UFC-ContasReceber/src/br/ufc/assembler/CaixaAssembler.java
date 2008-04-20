package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.Caixa;
import br.com.CaixaEntradaSaida;
import br.com.ContasPagar;
import br.com.ContasReceber;
import br.ufc.BO.CaixaEntradaSaidaBO;
import br.ufc.BO.ContasPagarBO;
import br.ufc.BO.ContasReceberBO;
import br.ufc.TO.CaixaTO;
import br.ufc.uteis.Status;

import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

public class CaixaAssembler implements ASSEMBLER<Caixa, CaixaTO> {
	
	public CaixaTO entity2EntityTO(Caixa entity) {
		final CaixaTO to = new CaixaTO();
		
		to.setDataPagamento(ConverteData.retornaData(entity.getDataPagamento()));
		to.setDescricaoServico(entity.getDescricaoServico());
		to.setDoc(entity.getDoc());
		to.setId(String.valueOf(entity.getId()));
		try {
			to.setIdCaixaEntradaSaida(String.valueOf(entity.getIdCaixaEntradaSaida().getId()));
		} catch (Exception e) {
			to.setIdCaixaEntradaSaida("");
		}
		try {
			to.setIdContasPagar(String.valueOf(entity.getIdContasPagar().getId()));
		} catch (Exception e) {
			to.setIdContasPagar("");
		}
		try {
			to.setIdContasReceber(String.valueOf(entity.getIdContasReceber().getId()));
		} catch (Exception e) {
			to.setIdContasReceber("");
		}
		
		to.setValor(ConverteNumero.converteNumero(entity.getValor()));
		to.setStatus(Status.retornaTipo(entity.getStatus()));
		return to;
	}

	public List<CaixaTO> entity2EntityTO(List<Caixa> entity) {
		final List<CaixaTO> listTO = new ArrayList<CaixaTO>();
		for (Caixa caixa : entity) {
			listTO.add(entity2EntityTO(caixa));
		}
		return listTO;
	}

	public Caixa entityTO2Entity(CaixaTO to) {

		final Caixa entity = new Caixa();
		try {
			entity.setDataPagamento(ConverteData.retornaData(to.getDataPagamento()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		entity.setDescricaoServico(to.getDescricaoServico());
		entity.setDoc(to.getDoc());
		try{
			entity.setId(Integer.parseInt(to.getId()));
			
		}catch (Exception e) {
		}
		
		try {
			ContasPagar cp = ContasPagarBO.getInstance().findById(Integer.parseInt(to.getIdContasPagar()));
			entity.setIdContasPagar(cp);
		} catch (Exception e) {
			entity.setIdContasPagar(null);
		}
		try {
			CaixaEntradaSaida ces = CaixaEntradaSaidaBO.getInstance().findById(Integer.parseInt(to.getIdCaixaEntradaSaida()));
			entity.setIdCaixaEntradaSaida(ces);
		} catch (Exception e) {
			entity.setIdCaixaEntradaSaida(null);
		}
		try {
			ContasReceber cr = ContasReceberBO.getInstance().findById(Integer.parseInt(to.getIdContasReceber()));
			entity.setIdContasReceber(cr);
		} catch (Exception e) {
			entity.setIdContasReceber(null);
		}
		try {
			entity.setValor(ConverteNumero.converteNumero(to.getValor()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entity.setStatus(Status.retornaTipo(to.getStatus()));
		return entity;

	}

	public List<Caixa> entityTO2Entity(List<CaixaTO> to) {
		final List<Caixa> listEntity = new ArrayList<Caixa>();
		for (CaixaTO caixaTO : to) {
			listEntity.add(entityTO2Entity(caixaTO));
		}
		return listEntity;
	}

	public static ASSEMBLER<Caixa, CaixaTO> getInstance() {
		return new CaixaAssembler();
	}

}