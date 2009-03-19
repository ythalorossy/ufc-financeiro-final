package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.ItensNotaFiscalTO;

import com.Auxiliar.Divisao;
import com.converte.ConverteData;

public class ItensNotaFiscalAssembler implements
		ASSEMBLER<ItensNotaFiscal, ItensNotaFiscalTO> {

	public ItensNotaFiscalTO entity2EntityTO(ItensNotaFiscal entity) {
		final ItensNotaFiscalTO to = new ItensNotaFiscalTO();
		to.setId(String.valueOf(entity.getId()));
		to.setIdCliente(String.valueOf(entity.getIdCliente()));
		to.setNomeCliente(retornaCliente(entity.getIdCliente()));
		
		to.setIdNotaFiscal(String.valueOf(entity.getIdNotaFiscal().getId()));
		to.setNumeroNF(retornaNF(entity.getIdNotaFiscal().getId()));
		
		to.setIdDivisao(String.valueOf(entity.getIdDivisao()));
		to.setNomeDivisao(retornaDivisao(entity.getIdDivisao()));
		
		to.setIdLaboratorio(String.valueOf(entity.getIdLaboratorio()));
		try {
			to.setNomeLaboratorio(entity.getNomeLaboratorio());
		} catch (Exception e) {
			to.setNomeLaboratorio("");
		}
		
		
		to.setQuantidade(String.valueOf(entity.getQuantidade()));
		to.setServico(entity.getServico());
		to.setValor(ConverteNumero.converteNumero(entity.getValor()));
		to.setValorTotal(ConverteNumero.converteNumero(entity.getValorTotal()));
		to.setStatus(String.valueOf(entity.getStatus()));
		to.setData(ConverteData.retornaData(entity.getData()));
		return to;

	}

	public List<ItensNotaFiscalTO> entity2EntityTO(List<ItensNotaFiscal> entity) {
		final List<ItensNotaFiscalTO> listTO = new ArrayList<ItensNotaFiscalTO>();
		for (ItensNotaFiscal ItensNotaFiscal : entity) {
			listTO.add(entity2EntityTO(ItensNotaFiscal));
		}
		return listTO;

	}

	public ItensNotaFiscal entityTO2Entity(ItensNotaFiscalTO to) {
		final ItensNotaFiscal entity = new ItensNotaFiscal();
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}

		try {
			entity.setIdCliente(to.getIdCliente());
		} catch (Exception e) {
		}
		try {
			entity.setIdDivisao(Integer.parseInt(to.getIdDivisao()));
			
		} catch (Exception e) {
			
		}
		entity.setNomeDivisao(retornaDivisao(entity.getIdDivisao()));
		entity.setIdNotaFiscal(retornaNF(to.getIdNotaFiscal()));
		entity.setNumeroNF(retornaNF(to.getIdNotaFiscal()).getNotaFiscal());

		try {
			entity.setQuantidade(Integer.parseInt(to.getQuantidade()));
		} catch (Exception e) {
		}
		entity.setServico(to.getServico());
		try {
			entity.setValor(ConverteNumero.converteNumero(to.getValor()));
			entity.setValorTotal(ConverteNumero.converteNumero(to.getValorTotal()));
		} catch (Exception e1) {
		}
		
		try {
			entity.setStatus(Integer.parseInt(to.getStatus()));
		} catch (Exception e) {
			
		}
		
		try {
			entity.setIdLaboratorio(Integer.parseInt(to.getIdLaboratorio()));
		} catch (Exception e) {
			entity.setIdLaboratorio(0);
		}
		
		try {
			entity.setNomeLaboratorio(new LaboratorioDAO().findById(Integer.parseInt(to.getIdLaboratorio())).getNome());
		} catch (Exception e) {
			entity.setNomeLaboratorio(to.getNomeLaboratorio());
		}

		return entity;

	}

	public List<ItensNotaFiscal> entityTO2Entity(List<ItensNotaFiscalTO> to) {
		final List<ItensNotaFiscal> listEntity = new ArrayList<ItensNotaFiscal>();
		for (ItensNotaFiscalTO ItensNotaFiscalTO : to) {
			listEntity.add(entityTO2Entity(ItensNotaFiscalTO));
		}
		return listEntity;

	}
	
	public String retornaCliente(String idCliente){
		return new ClienteAssembler().retornaCliente(idCliente);
	}
	
	public String retornaNF(int idNotaFiscal){
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(idNotaFiscal);
		return notaFiscal.getNotaFiscal();
	}

	public NotaFiscal retornaNF(String idNotaFiscal){
		final int id = Integer.parseInt(idNotaFiscal);
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(id);
		return notaFiscal;
	}
	
	public String retornaDivisao(int idDivisao){
		final Divisao divisao = new DivisaoDAO().findById(idDivisao);
		return divisao.getNome();
	}

}
