package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.ItensPedidoDespesa;
import br.com.PedidoDespesa;
import br.ufc.uteis.Status;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class PedidoDespesaDAO implements DAO<PedidoDespesa> {
	
	//criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	//Lista de notas fiscais
	private List<PedidoDespesa> list = new ArrayList<PedidoDespesa>();
	// Retorno padrão
	private boolean retorno = false;
	
	
	// Métodos que startam a transação, comitam, rollback e fecham a sessão
	public void commitTransaction() {
		hh.getSession().getTransaction().commit();
	}
	public void startTransaction() {
		hh.getSession().beginTransaction();
	}
	public void closeSession() {
		hh.getSession().clear();
	}
	public void rollbackTransaction(){
		hh.getSession().getTransaction().rollback();
	}
	
	public List<PedidoDespesa> findAll() {
		startTransaction();
		Query query = hh.getSession().createQuery("From PedidoDespesa order by dataPD");
		list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}

	public PedidoDespesa findById(int id) {
		startTransaction();
		final PedidoDespesa pd = (PedidoDespesa) hh.getSession().get(PedidoDespesa.class, id);
		commitTransaction();
		closeSession();
		return pd;
	}

	public boolean save(PedidoDespesa PedidoDespesa) {
		startTransaction();
		try{
			hh.getSession().saveOrUpdate(PedidoDespesa);
			retorno = true;
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	public boolean update(PedidoDespesa PedidoDespesa) {
		startTransaction();
		try{
			hh.getSession().update(PedidoDespesa);
			retorno = true;
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		} finally{
			closeSession();
		}
		return retorno;
	}

	public boolean delete(PedidoDespesa PedidoDespesa) {
			try{
				startTransaction();
				hh.delete(PedidoDespesa);
				commitTransaction();
				retorno = true;
			} catch (Exception e) {
				rollbackTransaction();
			} finally {
				closeSession();
			}
			
		return retorno;
	}
	
	public PedidoDespesa findByObject(PedidoDespesa e) {
		return null;
	}
	
	public List<PedidoDespesa> findByNumeroPD(String numeroPD) {
		startTransaction();
		final Query query= hh.getSession().createQuery("From PedidoDespesa where numeroPD = ?");
		query.setParameter(0, numeroPD);
		final List<PedidoDespesa> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public boolean update(PedidoDespesa pedidoDespesa,List<ItensPedidoDespesa> itens) {
		startTransaction();
		try {
			if (!itens.isEmpty()){
				for (ItensPedidoDespesa itensPedidoDespesa : itens) {
					hh.getSession().update(itensPedidoDespesa);
				}
			}
			hh.getSession().update(pedidoDespesa);
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}
	
	public List<PedidoDespesa> findPedido30(GregorianCalendar calendar) {
		startTransaction();
		final Query query = hh.getSession().createQuery("From PedidoDespesa where dataPD  >= ? and status = ?");
		query.setParameter(0, calendar);
		query.setParameter(1, Status.AGUARDANDO);
		final List<PedidoDespesa> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public List<PedidoDespesa> findByDay(GregorianCalendar dataInicial,GregorianCalendar dataFinal) {
		startTransaction();
		final Query query = hh.getSession().createQuery("From PedidoDespesa where dataPD between ? and ? order by dataPD" );
		query.setParameter(0, dataInicial);
		query.setParameter(1, dataFinal);
		final List<PedidoDespesa> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
}
