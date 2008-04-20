package br.ufc.DAO;

import java.util.List;

import br.com.ItensPedidoDespesa;
import br.com.PedidoDespesa;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class ItensPedidoDespesaDAO implements DAO<ItensPedidoDespesa> {
	

	private HibernateHelper hh = HibernateHelper.getInstance();
	private boolean retorno = false;
	
	// Métodos que startam a transação, comitam e fecham a sessão
	public void commitTransaction() {
		hh.getSession().getTransaction().commit();
	}
	public void startTransaction() {
		hh.getSession().beginTransaction();
	}
	public void closeSession() {
		hh.getSession().clear();
	}
	public void rollBackTransaction() {
		hh.getSession().getTransaction().rollback();
	}

	@Override
	public boolean delete(ItensPedidoDespesa e) {
		startTransaction();
		try {
			hh.delete(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollBackTransaction();
		} finally{
			closeSession();
		}
		return retorno;
	}

	
	@Override
	public List<ItensPedidoDespesa> findAll() {
		startTransaction();
		final List<ItensPedidoDespesa> list = hh.getSession().createQuery("From ItensPedidoDespesa").list();
		commitTransaction();
		closeSession();
		return list;
	}

	@Override
	public ItensPedidoDespesa findById(int id) {
		startTransaction();
		final ItensPedidoDespesa itensPedidoDespesa = (ItensPedidoDespesa) hh.getSession().get(ItensPedidoDespesa.class, id);
		commitTransaction();
		closeSession();
		return itensPedidoDespesa;
	}

	@Override
	public ItensPedidoDespesa findByObject(ItensPedidoDespesa e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(ItensPedidoDespesa e) {
		startTransaction();
		try {
			hh.save(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	@Override
	public boolean update(ItensPedidoDespesa e) {
		startTransaction();
		try {
			hh.getSession().update(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	public List<ItensPedidoDespesa> findAllItensByNumeroPD(PedidoDespesa e) {
		startTransaction();
		final List<ItensPedidoDespesa> list = hh.getSession().createQuery("From ItensPedidoDespesa where idPedidoDespesa = "+e.getId()).list();
		commitTransaction();
		closeSession();
		return list;
	}

	public double findsumCotado(int id) {
		startTransaction();
		final double soma = (Double) hh.getSession().createSQLQuery("Select sum(ipd_valor_total_cotado) from financeiro.itens_pedido_despesa where ipd_id_pedido_despesa = "+id).uniqueResult();
		commitTransaction();
		closeSession();
		return soma;
	}

	public double findsumPrevisto(int id) {
		startTransaction();
		final double soma = (Double) hh.getSession().createSQLQuery("Select sum(ipd_valor_total) from financeiro.itens_pedido_despesa where ipd_id_pedido_despesa = "+id).uniqueResult();
		commitTransaction();
		closeSession();
		return soma;
	}

	
	public boolean delete(List<ItensPedidoDespesa> list) {
		startTransaction();
		try {
			if (!list.isEmpty()){
				for (ItensPedidoDespesa itensPedidoDespesa : list) {
					hh.delete(itensPedidoDespesa);
				}
				commitTransaction();
				retorno = true;
			}
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

}
