package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.com.CaixaEntradaSaida;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class CaixaEntradaSaidaDAO implements DAO<CaixaEntradaSaida> {

	private HibernateHelper hh = HibernateHelper.getInstance();
	private List<CaixaEntradaSaida> caixaEntradaSaidaList = new ArrayList<CaixaEntradaSaida>();
	private CaixaEntradaSaida caixaEntradaSaida = new CaixaEntradaSaida();
	
	@SuppressWarnings("unused")
	private boolean result = false;

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

	public List<CaixaEntradaSaida> findAll() {
		startTransaction();
		try {
			caixaEntradaSaidaList = hh.getSession().createQuery("from CaixaEntradaSaida").list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}

		return caixaEntradaSaidaList;
	}

	public CaixaEntradaSaida findById(int id) {
		startTransaction();
		try {
			caixaEntradaSaida = (CaixaEntradaSaida) hh.getSession().get(CaixaEntradaSaida.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		
		return caixaEntradaSaida;
	}

	public boolean save(CaixaEntradaSaida e) {
		try {
			startTransaction();
			hh.getSession().save(e);
			commitTransaction();
			result = true;
		} catch (Exception ex) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return result;
	}
	
	public CaixaEntradaSaida saveReturn(CaixaEntradaSaida e) {
		try {
			startTransaction();
			hh.getSession().save(e);
			commitTransaction();
			result = true;
		} catch (Exception ex) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		
		return e;
	}

	public boolean update(CaixaEntradaSaida e) {
		return false;
	}

	public boolean delete(CaixaEntradaSaida e) {
		return false;
	}

	public CaixaEntradaSaida findByObject(CaixaEntradaSaida e) {

		CaixaEntradaSaida ces = null;

		try {
			startTransaction();
			Query query = hh.getSession().createQuery(
							"from CaixaEntradaSaida where operacao=? and dataTransacao=? and descricao=? and valor=?");
			query.setParameter(0, e.getOperacao());
			query.setParameter(1, e.getDataTransacao());
			query.setParameter(2, e.getDescricao());
			query.setParameter(3, e.getValor());

			ces = (CaixaEntradaSaida) query.uniqueResult();

			commitTransaction();
			

		} catch (Exception ex) {
			rollbackTransaction();
		} finally {
			closeSession();
		}

		return ces;
	}

}
