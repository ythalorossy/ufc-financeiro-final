package br.ufc.DAO;

import java.util.List;

import org.hibernate.Query;

import br.com.CaixaEntradaSaida;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class CaixaEntradaSaidaDAO implements DAO<CaixaEntradaSaida> {

	private HibernateHelper hh = HibernateHelper.getInstance();
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
		final List<CaixaEntradaSaida> caixaEntradaSaidaList = (List<CaixaEntradaSaida>) hh.getSession()
				.createQuery("from CaixaEntradaSaida").list();
		commitTransaction();
		closeSession();
		return caixaEntradaSaidaList;
	}

	public CaixaEntradaSaida findById(int id) {
		startTransaction();
		final CaixaEntradaSaida caixaEntradaSaida = (CaixaEntradaSaida) hh.getSession().get(
				CaixaEntradaSaida.class, id);
		commitTransaction();
		closeSession();
		return caixaEntradaSaida;
	}

	public boolean save(CaixaEntradaSaida e) {
		try {
			startTransaction();
			hh.getSession().save(e);
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {
			rollbackTransaction();
		}
		return result;
	}
	
	public CaixaEntradaSaida saveReturn(CaixaEntradaSaida e) {
		try {
			startTransaction();
			hh.getSession().save(e);
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {
			rollbackTransaction();
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
			closeSession();

		} catch (Exception ex) {
			rollbackTransaction();
		}

		return ces;
	}

}
