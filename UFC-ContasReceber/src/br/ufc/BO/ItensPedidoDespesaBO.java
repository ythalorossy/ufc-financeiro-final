package br.ufc.BO;

import java.util.List;

import br.com.ItensPedidoDespesa;
import br.com.PedidoDespesa;
import br.ufc.DAO.DAO;
import br.ufc.DAO.ItensPedidoDespesaDAO;
import br.ufc.DAO.PedidoDespesaDAO;

public class ItensPedidoDespesaBO implements BO<ItensPedidoDespesa> {
	
	private DAO<ItensPedidoDespesa> itensPedidoDespesaDAO = new ItensPedidoDespesaDAO();
	private boolean retorno = false;

	public List<ItensPedidoDespesa> findAllItensByNumeroPD(PedidoDespesa e) {

		final List<ItensPedidoDespesa> list = ((ItensPedidoDespesaDAO)itensPedidoDespesaDAO).findAllItensByNumeroPD(e);
		return list;
	}

	public static ItensPedidoDespesaBO getInstance() {
		return new ItensPedidoDespesaBO();
	}

	public double findSumCotado(PedidoDespesa pedidoDespesa) {
		return ((ItensPedidoDespesaDAO)itensPedidoDespesaDAO).findsumCotado(pedidoDespesa);
	}

	public double findSumPrevisto(PedidoDespesa pedidoDespesa) {
		return ((ItensPedidoDespesaDAO)itensPedidoDespesaDAO).findsumPrevisto(pedidoDespesa);
	}
	
	public boolean delete(List<ItensPedidoDespesa> list) {
		final PedidoDespesa pedidoDespesa = list.get(0).getIdPedidoDespesa();
		try {
			retorno = ((ItensPedidoDespesaDAO)itensPedidoDespesaDAO).delete(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		if(retorno){
			try {
				final double valor = findSumCotado(pedidoDespesa);
				pedidoDespesa.setValorPrevisto(valor);
			} catch (Exception e) {
				pedidoDespesa.setValorPrevisto(0);
			}
			retorno = new PedidoDespesaDAO().update(pedidoDespesa);
		}
		return retorno;	
	}
	
	public boolean save(ItensPedidoDespesa e) {
		// setando o valor total do item
		final double valor = e.getValorUnitario();
		final int quantidade = e.getQuantidade();
		final double valorTotalItem = valor * quantidade;
		e.setValorTotal(valorTotalItem);
		
		final double valorTotalCotado = e.getValorUnitarioCotado() * e.getQuantidade();
		e.setValorTotalCotado(valorTotalCotado);
		return itensPedidoDespesaDAO.save(e);
	}


	@Override
	public boolean delete(ItensPedidoDespesa e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItensPedidoDespesa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItensPedidoDespesa findById(int id) {
		return itensPedidoDespesaDAO.findById(id);
	}

	@Override
	public DAO<ItensPedidoDespesa> getDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ItensPedidoDespesa e) {
		// TODO Auto-generated method stub
		return false;
	}


}
