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
	private PedidoDespesa pd = new PedidoDespesa();
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
		try {
			Query query = hh.getSession().createQuery(
					"From PedidoDespesa order by dataPD");
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}

	public PedidoDespesa findById(int id) {
		startTransaction();
		try {
			pd = (PedidoDespesa) hh.getSession().get(PedidoDespesa.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
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
		try {
			final Query query = hh.getSession().createQuery(
					"From PedidoDespesa where numeroPD = ?");
			query.setParameter(0, numeroPD);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
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
		try {
			final Query query = hh.getSession().createQuery(
							"From PedidoDespesa where dataPD  >= ? and status = ? or status = ?");
			query.setParameter(0, calendar);
			query.setParameter(1, Status.AGUARDANDO);
			query.setParameter(2, Status.COTADO);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	
	public List<PedidoDespesa> findByDay(GregorianCalendar dataInicial,GregorianCalendar dataFinal) {
		startTransaction();
		try {
			final Query query = hh.getSession().createQuery(
							"From PedidoDespesa where dataPD between ? and ? order by dataPD");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	public List<PedidoDespesa> findAllAguardando() {
		startTransaction();
		try {
			final Query query = hh.getSession().createQuery(
					"From PedidoDespesa where status = ?");
			query.setParameter(0, Status.AGUARDANDO);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	public List<PedidoDespesa> findByPD(String pd) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("from PedidoDespesa where numeroPD = ?");
			query.setParameter(0, pd);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	
}
