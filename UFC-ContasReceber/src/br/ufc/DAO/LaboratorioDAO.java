package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import com.Auxiliar.Clientes;
import com.Auxiliar.Laboratorio;
import com.hibernate.HibernateHelperGlobal;

public class LaboratorioDAO {
	
	private HibernateHelperGlobal hg = HibernateHelperGlobal.getInstance();
	private List<Laboratorio> listLaboratorio = new ArrayList<Laboratorio>();

	private boolean result = false;

	public void commitTransaction() {
		hg.getSession().getTransaction().commit();
	}

	public void startTransaction() {
		hg.getSession().beginTransaction();
	}

	public void closeSession() {
		hg.getSession().clear();
	}
	

	public Laboratorio findById(int id) {
		startTransaction();
		Laboratorio laboratorio = (Laboratorio) hg.getSession().createSQLQuery("Select * From global.UNIDADES_PRODUCAO Where \"UNP_KEY\"="+ id).addEntity(Laboratorio.class).uniqueResult();
		commitTransaction();
		closeSession();
		return laboratorio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.ufc.DAO.DAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<Laboratorio> findAll() {
		startTransaction();
		listLaboratorio = (List<Laboratorio>) hg.getSession().createSQLQuery("select * from global.UNIDADES_PRODUCAO").addEntity(Laboratorio.class).list();
		commitTransaction();
		closeSession();
		return listLaboratorio;
	}

	public boolean save(Laboratorio e) {
		try {
			startTransaction();
			hg.save(e);
			commitTransaction();
			closeSession();
		} catch (Exception ex) {
		}

		return result;
	}

	public boolean update(Laboratorio e) {

		return result;

	}

	public boolean delete(Laboratorio e) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public Laboratorio findByName(String nomeLaboratorio) {
		startTransaction();
		listLaboratorio = (List<Laboratorio>) hg.getSession().createSQLQuery("select * From global.unidades_producao where \"UNP_NOME\" = "+nomeLaboratorio).addEntity(Laboratorio.class).list();
		commitTransaction();
		closeSession();
		return listLaboratorio.get(0);
	}

	public Clientes findByObject(Laboratorio e) {
		// TODO Auto-generated method stub
		return null;
	}

}