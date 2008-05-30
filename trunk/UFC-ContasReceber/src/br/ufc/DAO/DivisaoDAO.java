package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.Auxiliar.Divisao;
import com.hibernate.HibernateHelperGlobal;

@SuppressWarnings("unchecked")
public class DivisaoDAO {

	private HibernateHelperGlobal hg = HibernateHelperGlobal.getInstance();
	private Divisao divisao = new Divisao();
	private List<Divisao> listDivisao = new ArrayList<Divisao>();
	
	public void commitTransaction(){
		hg.getSession().getTransaction().commit();
	}
	public void startTransaction(){
		hg.getSession().beginTransaction();
	}
	public void closeSession(){
		hg.getSession().clear();
	}
	
	public void rollback (){
		hg.getSession().getTransaction().rollback();
	}
	
	public Divisao findById(int id){
		startTransaction();
		try {
			divisao = (Divisao) hg.getSession().get(Divisao.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollback();
		} finally {
			closeSession();
		}
		return divisao;
	}
	
	public List<Divisao> findAll(){
		startTransaction();
		try {

			listDivisao = (List<Divisao>) hg.getSession().createQuery("From Divisao").list();
			commitTransaction();
		} catch (Exception e) {
			rollback();
		} finally {
			closeSession();
		}
		return listDivisao;
	}
	public Divisao findByName(String nomeDivisao) {
		startTransaction();
		try {

			Query query = hg.getSession().createQuery("From Divisao where nome = ?");
			query.setParameter(0, nomeDivisao);
			divisao = (Divisao) query.uniqueResult();
			commitTransaction();
		} catch (Exception e) {
			rollback();
		}finally {
			closeSession();
		}
		return divisao;
	}
	
}
