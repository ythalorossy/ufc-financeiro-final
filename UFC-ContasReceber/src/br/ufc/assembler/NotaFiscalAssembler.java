package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.NotaFiscal;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.TO.NotaFiscalTO;
import br.ufc.uteis.Status;

import com.converte.ConverteData;

public class NotaFiscalAssembler implements ASSEMBLER<NotaFiscal, NotaFiscalTO> {

	public static ASSEMBLER<NotaFiscal, NotaFiscalTO> getInstance() {
		return new NotaFiscalAssembler();
	}

	public NotaFiscalTO entity2EntityTO(NotaFiscal entity) {
		final NotaFiscalTO to = new NotaFiscalTO();
		to.setId(String.valueOf(entity.getId()));
		to.setDataSaida(ConverteData.retornaData(entity.getDataSaida()));
		to.setIdCliente(String.valueOf(entity.getIdCliente()));
		to.setNomeCliente(retornaCliente(entity.getIdCliente()));
		to.setNumeroProcesso(entity.getNumeroProcesso());
		to.setNotaFiscal(entity.getNotaFiscal());
		to.setTipoNota(String.valueOf(entity.getTipoNota()));
		to.setValorNota(ConverteNumero.converteNumero(entity.getValorNota()));
		to.setStatus(Status.retornaTipo(entity.getStatus()));
		to.setNumeroContrato(entity.getNumeroContrato());
		to.setCancelamento(entity.getCancelamento());
		to.setObservacao(entity.getObservacao());
		try {
			String desconto =ConverteNumero.converteNumero(entity.getDesconto()); 
			to.setDesconto(desconto);
		} catch (Exception e) {
			to.setDesconto("");
		}
		
		return to;

	}

	public List<NotaFiscalTO> entity2EntityTO(List<NotaFiscal> entity) {
		final List<NotaFiscalTO> listTO = new ArrayList<NotaFiscalTO>();
		for (NotaFiscal notaFiscal : entity) {
			listTO.add(entity2EntityTO(notaFiscal));
		}
		return listTO;

	}

	public NotaFiscal entityTO2Entity(NotaFiscalTO to) {
		final NotaFiscal entity = new NotaFiscal();
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}
		try {
			entity.setStatus(Integer.parseInt(to.getStatus()));
		} catch (Exception e) {
			entity.setStatus(Status.ABERTO);
		}
		entity.setNumeroProcesso(to.getNumeroProcesso());
		entity.setNumeroContrato(to.getNumeroContrato());
		try {
			entity.setDataSaida(ConverteData.retornaData(to.getDataSaida()));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			entity.setIdCliente(to.getIdCliente());
		} catch (Exception e) {

		}

		entity.setNotaFiscal(to.getNotaFiscal());
		try {
			entity.setTipoNota(Integer.parseInt(to.getTipoNota()));
		} catch (Exception e) {

		}

		try {
			entity.setValorNota(ConverteNumero
					.converteNumero(to.getValorNota()));
		} catch (Exception e) {
			entity.setValorNota(0.00);
		}
		
		try {
			entity.setDesconto(ConverteNumero.converteNumero(to.getDesconto()));
		} catch (Exception e) {
			entity.setDesconto(new Double(0));
		}
		
		try {
			entity.setCancelamento(to.getCancelamento());
		} catch (Exception e) {
			entity.setCancelamento("");
		}
		
		try {
			entity.setObservacao(to.getObservacao());
		} catch (Exception e) {
			entity.setObservacao("");
		}
		
		
		return entity;

	}

	public List<NotaFiscal> entityTO2Entity(List<NotaFiscalTO> to) {
		final List<NotaFiscal> listEntity = new ArrayList<NotaFiscal>();
		for (NotaFiscalTO notaFiscalTO : to) {
			listEntity.add(entityTO2Entity(notaFiscalTO));
		}
		return listEntity;

	}

	public String retornaCliente(String idCliente){
		return new ClienteAssembler().retornaCliente(idCliente);
	}

	public String retornaNF(int idNotaFiscal) {
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(
				idNotaFiscal);
		return notaFiscal.getNotaFiscal();
	}

}
