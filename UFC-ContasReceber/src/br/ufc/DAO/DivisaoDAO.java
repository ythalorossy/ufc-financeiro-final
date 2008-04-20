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
	
	public Divisao findById(int id){
		startTransaction();
		Divisao divisao = (Divisao) hg.getSession().get(Divisao.class, id);
		commitTransaction();
		closeSession();
		return divisao;
	}
	
	public List<Divisao> findAll(){
		startTransaction();
		listDivisao = (List<Divisao>) hg.getSession().createQuery("From Divisao").list();
		commitTransaction();
		closeSession();
		return listDivisao;
	}
	public Divisao findByName(String nomeDivisao) {
		startTransaction();
		Query query = hg.getSession().createQuery("From Divisao where nome = ?");
		query.setParameter(0, nomeDivisao);
		divisao = (Divisao) query.uniqueResult();
		commitTransaction();
		closeSession();
		return divisao;
	}
	
}
