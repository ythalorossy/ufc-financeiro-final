package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.Auxiliar.Clientes;
import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;
import com.hibernate.HibernateHelperGlobal;

public class LaboratorioDAO {

	private HibernateHelperGlobal hg = HibernateHelperGlobal.getInstance();
	private Laboratorio laboratorio = new Laboratorio();
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
		laboratorio = (Laboratorio) hg.getSession().get(Laboratorio.class, id);
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
		listLaboratorio = (List<Laboratorio>) hg.getSession().createQuery(
				"From Laboratorio").list();
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
		Query query = hg.getSession().createQuery(
				"From Laboratorio where nome = ?");
		query.setParameter(0, nomeLaboratorio);
		listLaboratorio = query.list();
		commitTransaction();
		closeSession();
		return listLaboratorio.get(0);
	}

	public Clientes findByObject(Laboratorio e) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Pesquisa todos os Laboratorios de uma divisao
	 * 
	 * @param divisao
	 *            Divisão usada para pesquisar os laboratorios
	 * @return Lista de laboratorios
	 */
	@SuppressWarnings("unchecked")
	public List<Laboratorio> findByIdDivisao(Divisao divisao) {
		startTransaction();
		Query query = hg.getSession().createQuery(
				"From Laboratorio where key = ?");
		query.setParameter(0, divisao.getId());
		listLaboratorio = query.list();
		commitTransaction();
		closeSession();
		return listLaboratorio;
	}

}