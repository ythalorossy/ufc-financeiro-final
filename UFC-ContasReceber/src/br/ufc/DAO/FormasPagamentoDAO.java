package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import br.com.FormasPagamento;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class FormasPagamentoDAO implements DAO<FormasPagamento> {

	private HibernateHelper hh = HibernateHelper.getInstance();
	private FormasPagamento formaPagamento = new FormasPagamento();
	private List<FormasPagamento> formasPagamento = new ArrayList<FormasPagamento>();
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

	public List<FormasPagamento> findAll() {

		startTransaction();
		formasPagamento = (List<FormasPagamento>) hh.getSession().createQuery(
				"from FormasPagamento").list();
		commitTransaction();
		closeSession();
		return formasPagamento;
	}

	public FormasPagamento findById(int id) {
		startTransaction();
		formaPagamento = (FormasPagamento) hh.getSession().get(
				FormasPagamento.class, id);
		commitTransaction();
		closeSession();
		return formaPagamento;
	}

	public boolean save(FormasPagamento e) {

		try {
			startTransaction();
			hh.getSession().save(e);
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {
		}

		return result;
	}

	public boolean update(FormasPagamento e) {
		try {
			startTransaction();
			hh.getSession().update(e);
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {
		}

		return result;

	}

	public boolean delete(FormasPagamento e) {

		try {
			startTransaction();
			hh.getSession().delete(e);
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {
		}

		return result;
	}

	public boolean delete(List<FormasPagamento> list) {

		try {
			startTransaction();
			for (FormasPagamento fp : list) {
				hh.delete(fp);
			}
			commitTransaction();
			result = true;
		} catch (Exception e) {

		}
		closeSession();

		return result;
	}

	@Override
	public FormasPagamento findByObject(FormasPagamento e) {
		// TODO Auto-generated method stub
		return null;
	}

}
