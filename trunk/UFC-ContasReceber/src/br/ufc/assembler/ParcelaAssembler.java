package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.NotaFiscal;
import br.com.Parcela;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.TO.ParcelaTO;

import com.Auxiliar.Clientes;
import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

public class ParcelaAssembler implements ASSEMBLER<Parcela, ParcelaTO> {
	
	public static ASSEMBLER<Parcela, ParcelaTO> getInstance(){
		return new ParcelaAssembler();
	}

	public ParcelaTO entity2EntityTO(Parcela entity) {
		final ParcelaTO to = new ParcelaTO();
		
		to.setId(String.valueOf(entity.getId()));
		to.setIdCliente(String.valueOf(entity.getIdCliente()));
		to.setNomeCliente(retornaCliente(entity.getIdCliente()));
		
		to.setIdNotaFiscal(String.valueOf(entity.getIdNotaFiscal().getId()));
		to.setNumeroNF(retornaNF(entity.getIdNotaFiscal()));
		
		to.setStatus(String.valueOf(entity.getStatus()));
		to.setDataPagamento(ConverteData.retornaData(entity.getDataPagamento()));
		to.setValor(ConverteNumero.converteNumero(entity.getValor()));
		to.setNumeroParcela(String.valueOf(entity.getNumeroParcela()));
		return to;

	}

	public List<ParcelaTO> entity2EntityTO(List<Parcela> entity) {
		final List<ParcelaTO> listTO = new ArrayList<ParcelaTO>();
		for (Parcela parcela : entity) {
			listTO.add(entity2EntityTO(parcela));
		}
		return listTO;
	}

	public Parcela entityTO2Entity(ParcelaTO to) {
		final Parcela entity = new Parcela();
		
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}
		
		entity.setIdCliente(to.getIdCliente());
		
		entity.setIdNotaFiscal(retornaNF(to.getIdNotaFiscal()));

		try {
			entity.setNumeroParcela(Integer.parseInt(to.getNumeroParcela()));
		} catch (Exception e) {
			entity.setNumeroParcela(0);
		}
		entity.setStatus(Integer.parseInt(to.getStatus()));
		try {
			entity.setDataPagamento(ConverteData.retornaData(to.getDataPagamento()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			entity.setValor(ConverteNumero.converteNumero(to.getValor()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entity;

	
	}

	public List<Parcela> entityTO2Entity(List<ParcelaTO> to) {
		final List<Parcela> listEntity = new ArrayList<Parcela>();
		for (ParcelaTO parcelaTO : to) {
			listEntity.add(entityTO2Entity(parcelaTO));
		}
		return listEntity;
	}
	
	public String retornaNF(NotaFiscal idNotaFiscal){
		NotaFiscal notaFiscal = idNotaFiscal;
		return notaFiscal.getNotaFiscal();
	}
	
	public NotaFiscal retornaNF(String idNotaFiscal){
		final int id = Integer.parseInt(idNotaFiscal);
		final NotaFiscal notaFiscal = new NotaFiscalBO().findById(id);
		return notaFiscal;
	}
	
	public String retornaCliente(String idCliente){
		final Clientes cliente = new ClientesDAO().findById(idCliente);
		return cliente.getNome();
	}

}
