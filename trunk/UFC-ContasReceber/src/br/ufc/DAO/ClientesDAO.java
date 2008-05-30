package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.Auxiliar.Clientes;
import com.hibernate.HibernateHelperCliente;

@SuppressWarnings("unchecked")
public class ClientesDAO {

	private HibernateHelperCliente hg = HibernateHelperCliente.getInstance();
	private Clientes cliente = new Clientes();
	private List<Clientes> listClientes = new ArrayList<Clientes>();

	private boolean result = false;

	public void commitTransaction() {
		hg.getSession().getTransaction().commit();
	}
	
	public void rollbackTransaction() {
		hg.getSession().getTransaction().rollback();
	}


	public void startTransaction() {
		hg.getSession().beginTransaction();
	}

	public void closeSession() {
		hg.getSession().clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufc.DAO.DAO#findById(int)
	 */
	public Clientes findById(String id) {
		startTransaction();
		try {
			Query query = hg.getSession().createQuery("from Clientes Where id = ?");
			query.setParameter(0, id);
			cliente = (Clientes) query.uniqueResult();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return cliente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufc.DAO.DAO#findAll()
	 */
	public List<Clientes> findAll() {
		startTransaction();
		try {
			listClientes = (List<Clientes>) hg.getSession().createQuery("From Clientes").list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return listClientes;
	}

	public boolean save(Clientes e) {
		try {
			startTransaction();
			hg.save(e);
			commitTransaction();
		} catch (Exception ex) {
		} finally {
			closeSession();
		}

		return result;
	}

	public boolean update(Clientes e) {

		return result;

	}

	public boolean delete(Clientes e) {
		return false;
	}

	public Clientes findByName(String nomeCliente) {
		startTransaction();
		try {
			listClientes = (List<Clientes>) hg.getSession().createQuery("From Clientes where nome ='" 
							+ nomeCliente + "'").list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return listClientes.get(0);
	}

	/**
	 * Busca todos os Clientes que tem o cgcpf iniciando com o prefixo passado
	 * 
	 * @param prefixCGCPF
	 *            Prefixo usado na busca dos Clientes
	 * @return Lista de Clientes
	 */
	public List<Clientes> findByPrefixCGCPF(String prefixCGCPF) {
		startTransaction();
		try {
		listClientes = hg.getSession().createCriteria(Clientes.class).add(Restrictions.like("id", prefixCGCPF + "%"))
					.addOrder(Order.asc("nome")).list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return listClientes;
	}

	public Clientes findByObject(Clientes e) {
		return null;
	}

}
