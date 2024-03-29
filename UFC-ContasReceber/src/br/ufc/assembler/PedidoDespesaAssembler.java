package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.PedidoDespesa;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.PedidoDespesaTO;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

public class PedidoDespesaAssembler implements
		ASSEMBLER<PedidoDespesa, PedidoDespesaTO> {

	public static PedidoDespesaAssembler getInstance() {
		return new PedidoDespesaAssembler();
	}

	public PedidoDespesaTO entity2EntityTO(PedidoDespesa entity) {
		final PedidoDespesaTO to = new PedidoDespesaTO();
		 to.setDataPD(ConverteData.retornaData(entity.getDataPD()));
		 to.setFonteRecurso(entity.getFonteRecurso());
		 to.setId(String.valueOf(entity.getId()));
		 to.setIdDivisao(String.valueOf(entity.getIdDivisao()));
		 to.setJustificativa(entity.getJustificativa());
		 to.setNumeroPD(entity.getNumeroPD());
		 to.setOrcamento(entity.getOrcamento());
		 to.setStatus(Status.retornaTipo(entity.getStatus()));
		 to.setTipoServico(entity.getTipoServico());
		 to.setIdLaboratorio(String.valueOf(entity.getIdLaboratorio()));
		 to.setValorCotado(ConverteNumero.converteNumero(entity.getValorCotado()));
		 to.setValorPrevisto(ConverteNumero.converteNumero(entity.getValorPrevisto()));
		 try {
			 to.setNomeLaboratorio(new LaboratorioDAO().findById(entity.getIdLaboratorio()).getNome());
			 to.setNomeDivisao(new DivisaoDAO().findById(entity.getIdDivisao()).getNome());
		 } catch (Exception e) {
			 // TODO: handle exception
		 }
		 
		 to.setProjeto(Status.retornaTipo(entity.getProjeto()));
		 try {
			 to.setAnexo(Status.retornaTipo(entity.getAnexos()));
		 } catch (Exception e) {
			 to.setAnexo("");
		 }
		return to;
	}

	public List<PedidoDespesaTO> entity2EntityTO(List<PedidoDespesa> entity) {
		final List<PedidoDespesaTO> toList = new ArrayList<PedidoDespesaTO>();
		for (PedidoDespesa pedidoDespesa : entity) {
			toList.add(entity2EntityTO(pedidoDespesa));
		}
		return toList;
	}

	public PedidoDespesa entityTO2Entity(PedidoDespesaTO to) {
		final PedidoDespesa entity = new PedidoDespesa();
		
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
			entity.setId(0);
		}
		
		try {
			entity.setDataPD(ConverteData.retornaData(to.getDataPD()));
		} catch (Exception e) {

		}
		
		try {
			entity.setIdDivisao(Integer.parseInt(to.getIdDivisao()));
		} catch (Exception e) {

		}
		
		try {
			entity.setValorPrevisto(ConverteNumero.converteNumero(to.getValorPrevisto()));
		} catch (Exception e) {
			entity.setValorPrevisto(0);
		}
		
		try {
			entity.setValorCotado(ConverteNumero.converteNumero(to.getValorCotado()));
		} catch (Exception e) {
			entity.setValorCotado(0);
		}
		
		entity.setFonteRecurso(to.getFonteRecurso());
		entity.setJustificativa(to.getJustificativa());
		entity.setNumeroPD(to.getNumeroPD());
		entity.setOrcamento(to.getOrcamento());
		try {
			entity.setProjeto(Status.retornaTipo(to.getProjeto()));
		} catch (Exception e) {
			entity.setProjeto(0);
		}
		
		entity.setAnexos(Status.retornaTipo(to.getAnexo()));
		
		try {
			entity.setStatus(Status.retornaTipo(to.getStatus()));
		} catch (Exception e) {
			entity.setStatus(Status.ABERTO);
		}
		entity.setTipoServico(to.getTipoServico());
		
		try {
			entity.setIdLaboratorio(Integer.parseInt(to.getIdLaboratorio()));
		} catch (Exception e) {
		}
		
		return entity;
	}

	public List<PedidoDespesa> entityTO2Entity(List<PedidoDespesaTO> to) {
		final List<PedidoDespesa> listPD = new ArrayList<PedidoDespesa>();
		for (PedidoDespesaTO pedidoDespesaTO : to) {
			listPD.add(entityTO2Entity(pedidoDespesaTO));
		}
		return listPD;
	}

}
