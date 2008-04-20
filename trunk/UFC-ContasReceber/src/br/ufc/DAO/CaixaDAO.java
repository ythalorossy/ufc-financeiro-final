package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.Caixa;
import br.com.CaixaEntradaSaida;
import br.ufc.uteis.Status;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unused")
public class CaixaDAO implements DAO<Caixa> {

	private HibernateHelper hh = HibernateHelper.getInstance();

	private Caixa caixa = new Caixa();
	private List<Caixa> listCaixa = new ArrayList<Caixa>();
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

	/**
	 * Não implementado!!!!
	 */
	@SuppressWarnings("unchecked")
	public List<Caixa> findAll() {
		startTransaction();
		final List<Caixa> list = hh.getSession().createQuery("From Caixa").list();
		commitTransaction();
		closeSession();
		return list;
	}

	/**
	 * Retorna um Caixa por um Id
	 */
	public Caixa findById(int id) {

		try {
			startTransaction();
			caixa = (Caixa) hh.getSession().get(Caixa.class, id);
			hh.getSession().getTransaction().commit();
		} catch (Exception e) {
			hh.getSession().getTransaction().rollback();
		} finally {
			hh.closeSession();
		}

		return caixa;
	}

	/**
	 * Salva um caixa
	 */
	public boolean save(Caixa e) {
		try {
			startTransaction();
			hh.save(e);
			commitTransaction();
			result = true;
		} finally {
			closeSession();
		}

		return result;
	}

	/**
	 * Atualiza um caixa
	 */
	public boolean update(Caixa e) {
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

	/**
	 * Bater Caixa: Muda o status para Batido de todos os caixas de uma detemrinada data 
	 * @param calendar
	 * @return
	 */
	public boolean baterCaixa(List<Caixa> listCaixa) {

		try {
			startTransaction();

			/**
			 * Apenas Atualiza os Caixas.
			 */
			for (Caixa c : listCaixa) {
				hh.getSession().update(c);
			}
			commitTransaction();
			closeSession();
			result = true;
		} catch (Exception ex) {

		}

		return result;
	}

	/**
	 * Reabrir Caixa: Muda o status para Aguardando de todos os caixas de uma determinada data
	 * @param calendar
	 * @return
	 */
	public boolean reabrirCaixa(List<Caixa> caixaList) {

		
		
		try {
			startTransaction();
		
			for (Caixa c : caixaList) {
				/*
				 * Atualiza o Status para Aguardando de todos os
				 * caixas
				 */
				c.setStatus(Status.AGUARDANDO);
				hh.getSession().update(c);
			}
			commitTransaction();
			result = true;
			
		} catch (Exception ex){
			hh.getSession().getTransaction().rollback();
		} finally {
			closeSession();
		}
			
		return result;

	}

	/**
	 * Deleta um Caixa
	 */
	public boolean delete(Caixa e) {

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

	/**
	 * Retorna todos os caixas de uma detemrinada data
	 * @param calendar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Caixa> findAllByDia(GregorianCalendar calendar) {
		try {
			startTransaction();
			Query query = hh.getSession().createQuery("from Caixa where dataPagamento=?");
			query.setParameter(0, calendar);
			listCaixa = (List<Caixa>) query.list();
			commitTransaction();
		} catch (Exception ex) {
			closeSession();
		}

		return listCaixa;
	}

	/**
	 * Retorna todos os caixas anteriores a uma determinada data
	 * @param calendar
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Caixa> findAllAnterior(GregorianCalendar calendar) {

		try {
			startTransaction();

			Query query = hh.getSession().createQuery("from Caixa where dataPagamento<?");
			query.setParameter(0, calendar);
			
			listCaixa = (List<Caixa>) query.list();
			
			commitTransaction();
			
		} catch (Exception ex) {
			closeSession();
		}

		return listCaixa;
	}

	public Caixa findByObject(Caixa e) {
		return null;
	}

	/**
	 * Extornar Caixa do tipo Entrada e Saida
	 * @param e
	 * @param ces
	 * @return
	 */
	public boolean extornarCaixa(Caixa e, CaixaEntradaSaida ces) {

		try {
			startTransaction();
			/*
			 * Deleta o Caixa
			 */
			hh.getSession().delete(e);
			/*
			 * Deleta o Caixa Entrada e Saida
			 */
			hh.getSession().delete(ces);
			
			hh.getSession().getTransaction().commit();
			
			result = true;
		} catch (Exception ex) {
			hh.getSession().getTransaction().rollback();
		} finally {
			hh.closeSession();
		}

		return result;
	}

	public Caixa findByIdContaPagar(int id) {
		startTransaction();
		final Caixa caixa = (Caixa) hh.getSession().createQuery("From Caixa where idContasPagar = "+id).uniqueResult();
		commitTransaction();
		closeSession();
		return caixa;
	}
	
	public Caixa findByIdContaReceber(int id) {
		startTransaction();
		final Caixa caixa = (Caixa) hh.getSession().createQuery("From Caixa where idContasReceber = "+id).uniqueResult();
		commitTransaction();
		closeSession();
		return caixa;
	}

}
