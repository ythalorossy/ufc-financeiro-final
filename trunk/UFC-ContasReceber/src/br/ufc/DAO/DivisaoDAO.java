package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

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

		Divisao divisao = (Divisao) hg.getSession().createSQLQuery("Select * From global.DIVISOES Where \"DIV_KEY\"="+ id).addEntity(Divisao.class).uniqueResult();
		
		commitTransaction();
		closeSession();
		return divisao;
	}
	
	public List<Divisao> findAll(){
		startTransaction();
		listDivisao = (List<Divisao>) hg.getSession().createSQLQuery("Select * from global.DIVISOES").addEntity(Divisao.class).list();
		commitTransaction();
		closeSession();
		return listDivisao;
	}
	public Divisao findByName(String nomeDivisao) {
		startTransaction();
		divisao = (Divisao) hg.getSession().createSQLQuery("Select * from global.DIVISOES where \"DIV_NOME\" ='"+nomeDivisao+"'").addEntity(Divisao.class).uniqueResult();
		commitTransaction();
		closeSession();
		return divisao;
	}
	
}
