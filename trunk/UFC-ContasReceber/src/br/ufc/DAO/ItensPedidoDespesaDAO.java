package br.ufc.DAO;

import java.util.List;

import org.hibernate.Query;

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

	public double findsumCotado(PedidoDespesa id) {
		double soma = 0;
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("Select new ItensPedidoDespesa(sum(valorTotal), sum(valorTotalCotado)) " +
			"from ItensPedidoDespesa where idPedidoDespesa = ?");
			query.setParameter(0, id);
			List<ItensPedidoDespesa> itens = query.list();
			for (ItensPedidoDespesa i:itens){
				soma += i.getValorTotalCotado();
			}

			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}

			return soma;
	}

	public double findsumPrevisto(PedidoDespesa id) {
		double soma = 0;
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("Select new ItensPedidoDespesa(sum(valorTotal), sum(valorTotalCotado)) " +
			"from ItensPedidoDespesa where idPedidoDespesa = ?");
			query.setParameter(0, id);
			List<ItensPedidoDespesa> itens = query.list();
			for (ItensPedidoDespesa i:itens){
				soma += i.getValorTotal();
			}

			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}

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
	
	/**
	 * Método que realizar a cotação do produto
	 * @param itensPD
	 * @param pedidoDespesa
	 * @return
	 */
	public boolean cotar(List<ItensPedidoDespesa> itensPD, PedidoDespesa pedidoDespesa) {
		startTransaction();
		try {
			hh.getSession().update(pedidoDespesa);
			if (!itensPD.isEmpty()){
				for (ItensPedidoDespesa i : itensPD){
					hh.getSession().update(i);
				}
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}
		
		return retorno;
	}

}
