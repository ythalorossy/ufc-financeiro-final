package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.com.AcompanhamentoPD;
import br.com.PedidoDespesa;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class AcompanhamentoPdDAO {

	private HibernateHelper hh = HibernateHelper.getInstance();
	private List<AcompanhamentoPD> listAPD = new ArrayList<AcompanhamentoPD>();

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
	
	public void rollback(){
		hh.getSession().getTransaction().rollback();
	}

	
	public List<AcompanhamentoPD> findByPD(PedidoDespesa pedidoDespesa) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("from AcompanhamentoPD where pd = ?");
			query.setParameter(0, pedidoDespesa);
			listAPD = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollback();
		} finally {
			closeSession();
		}
		return listAPD;
	}

	public boolean save(AcompanhamentoPD apd) {
		startTransaction();
		try {
			hh.getSession().save(apd);
			commitTransaction();
			result = true;
		} catch (Exception e) {
			rollback();
		} finally{
			closeSession();
		}
		return result;
	}
	
	

}
