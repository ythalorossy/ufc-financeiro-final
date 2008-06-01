package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.ItensPedidoDespesa;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.TO.ItensPedidoDespesaTO;
import br.ufc.uteis.Status;

public class ItensPedidoDespesaAssembler {
	
	public ItensPedidoDespesaTO entity2EntityTO(ItensPedidoDespesa entity) {
		final ItensPedidoDespesaTO to = new ItensPedidoDespesaTO();
		to.setId(String.valueOf(entity.getId()));
		to.setIdDivisao(String.valueOf(entity.getIdDivisao()));
		to.setIdPedidoDespesa(String.valueOf(entity.getIdPedidoDespesa().getId()));
		to.setItem(entity.getItem());
		to.setNomeDivisao(new DivisaoDAO().findById(entity.getIdDivisao()).getNome());
		to.setQuantidade(String.valueOf(entity.getQuantidade()));
		to.setStatus(Status.retornaTipo(entity.getStatus()));
		to.setUnidade(entity.getUnidade());
		to.setValorTotal(ConverteNumero.converteNumero(entity.getValorTotal()));
		to.setValorTotalCotado(ConverteNumero.converteNumero(entity.getValorTotalCotado()));
		to.setValorUnitario(ConverteNumero.converteNumero(entity.getValorUnitario()));
		to.setValorUnitarioCotado(ConverteNumero.converteNumero(entity.getValorUnitarioCotado()));
		return to;

	}

	public List<ItensPedidoDespesaTO> entity2EntityTO(List<ItensPedidoDespesa> entity) {
		final List<ItensPedidoDespesaTO> listTO = new ArrayList<ItensPedidoDespesaTO>();
		for (ItensPedidoDespesa ItensPedidoDespesa : entity) {
			listTO.add(entity2EntityTO(ItensPedidoDespesa));
		}
		return listTO;

	}

	public ItensPedidoDespesa entityTO2Entity(ItensPedidoDespesaTO to) {
		final ItensPedidoDespesa entity = new ItensPedidoDespesa();
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}

		try {
			entity.setIdDivisao(Integer.parseInt(to.getIdDivisao()));
		} catch (Exception e) {
			
		}
		try {
			entity.setIdPedidoDespesa(PedidoDespesaBO.getInstance().findById(Integer.parseInt(to.getIdPedidoDespesa())));
		} catch (Exception e) {
			
		}
		try {
			entity.setQuantidade(Integer.parseInt(to.getQuantidade()));
		} catch (Exception e) {
			
		}

		entity.setItem(to.getItem());
		entity.setNomeDivisao(new DivisaoDAO().findById(Integer.parseInt(to.getIdDivisao())).getNome());
		try {
			entity.setStatus(Status.retornaTipo(to.getStatus()));
		} catch (Exception e) {
			entity.setStatus(Status.ABERTO);
		}
		
		entity.setUnidade(to.getUnidade());
		
		try {
			entity.setValorTotal(ConverteNumero.converteNumero(to.getValorTotal()));
			
		} catch (Exception e) {
		}
		
		try {
			entity.setValorTotalCotado(ConverteNumero.converteNumero(to.getValorTotalCotado()));
			
		} catch (Exception e) {
		}
		
		try {
			entity.setValorUnitario(ConverteNumero.converteNumero(to.getValorUnitario()));
			
		} catch (Exception e) {
		}

		try {
			entity.setValorUnitarioCotado(ConverteNumero.converteNumero(to.getValorUnitarioCotado()));
		} catch (Exception e) {
		}

		return entity;

	}

	public List<ItensPedidoDespesa> entityTO2Entity(List<ItensPedidoDespesaTO> to) {
		final List<ItensPedidoDespesa> listEntity = new ArrayList<ItensPedidoDespesa>();
		for (ItensPedidoDespesaTO ItensPedidoDespesaTO : to) {
			listEntity.add(entityTO2Entity(ItensPedidoDespesaTO));
		}
		return listEntity;

	}
	
}
