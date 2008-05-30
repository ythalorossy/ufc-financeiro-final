package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.Caixa;
import br.com.ContasPagar;
import br.com.Parcela;
import br.com.PedidoDespesa;
import br.ufc.uteis.Status;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class ContasPagarDAO implements DAO<ContasPagar> {

	// criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	// Lista de notas fiscais
	private List<ContasPagar> listContasPagar = new ArrayList<ContasPagar>();
	
	private ContasPagar contasPagar = new ContasPagar();

	// Retorno padrão
	private boolean retorno = false;

	// Métodos que startam a transação, comitam, rollback e fecham a sessão
	public void commitTransaction() {
		hh.getSession().getTransaction().commit();
	}

	public void startTransaction() {
		hh.getSession().beginTransaction();
	}

	public void closeSession() {
		hh.getSession().clear();
	}

	public void rollbackTransaction() {
		hh.getSession().getTransaction().rollback();
	}

	public List<ContasPagar> findAll() {

		try {
			startTransaction();
			listContasPagar = hh.getSession().createQuery(
					"from ContasPagar where status=" + Status.ABERTO + " order by dataPrevista").list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}

		return listContasPagar;
	}
	
	public List<ContasPagar> findAll(GregorianCalendar calendar) {

		try {
			startTransaction();
			Query query = hh.getSession().createQuery("from ContasPagar where status=? And dataPrevista=? order by dataPrevista");
			query.setParameter(0, Status.ABERTO);
			query.setParameter(1, calendar);
			listContasPagar = query.list();
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}

		return listContasPagar;
	}
	

	@Override
	public ContasPagar findById(int id) {
		startTransaction();
		try {
			contasPagar = (ContasPagar) hh.getSession().get(ContasPagar.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return contasPagar;
	}

	@Override
	public boolean save(ContasPagar e) {
		startTransaction();
		try {
			hh.getSession().save(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	public boolean update(ContasPagar e, Parcela parcela, Caixa caixa) {
		startTransaction();
		try {
			hh.getSession().update(e);
			hh.getSession().update(parcela);
			if (caixa != null){
				hh.delete(caixa);
			}
			commitTransaction();
			retorno = true;
			
		}catch (Exception ex) {
			rollbackTransaction();
		}finally {
			closeSession();
		}

		return retorno;
	}

	public List<ContasPagar> findContasPagarDia(GregorianCalendar calendar) {
		List<ContasPagar> listContasPagar = new ArrayList<ContasPagar>();
		try {
			startTransaction();
			Query query = hh.getSession().createQuery("from ContasPagar where dataPrevista=? order by dataPrevista");
			query.setParameter(0, calendar);
			listContasPagar = query.list();
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}

		return listContasPagar;
	}

	@Override
	public boolean delete(ContasPagar e) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ContasPagar> findAllContasByNf(int id) {
		startTransaction();
		try {
			listContasPagar = hh.getSession().createQuery("From ContasPagar where idNotaFiscal = " + id).list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return listContasPagar;
	}

	public ContasPagar findAllByIParcela(int id) {
		startTransaction();
		try {
			contasPagar = (ContasPagar) hh.getSession()
			.createQuery("From ContasReceber where idParcela = " + id)
			.uniqueResult();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return contasPagar;
	}

	@Override
	public ContasPagar findByObject(ContasPagar e) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean save(List<ContasPagar> contasPagarToSave) {
		startTransaction();
		try {
			for (int i = 0; i < contasPagarToSave.size(); i++) {
				hh.save(contasPagarToSave.get(i));
			}
			commitTransaction();
			retorno=true;
		} catch (Exception e) {
			rollbackTransaction();
		}finally{
			closeSession();
		}
		return retorno;
	}

	@Override
	public boolean update(ContasPagar e) {
		startTransaction();
		try {
			hh.getSession().update(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollbackTransaction();
		} finally{
			closeSession();
		}
		return retorno;
	}

	public List<ContasPagar> findfindByIdPD(int id) {
		startTransaction();
		try {
			listContasPagar = hh.getSession().createQuery("From ContasPagar where idPedidoDespesa = "+id).list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return listContasPagar;
	}

	public boolean delete(List<ContasPagar> list) {
		startTransaction();
		try {
			if (!list.isEmpty()){
				for (ContasPagar contasPagar : list) {
					hh.delete(contasPagar);
				}
				commitTransaction();
				retorno = true;
			}
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	public boolean update(ContasPagar e, PedidoDespesa pd, Caixa caixa) {
		startTransaction();
		try {
			hh.getSession().update(e);
			hh.getSession().update(pd);
			if (caixa != null){
				hh.delete(caixa);
			}
			commitTransaction();
			retorno = true;
			
		}catch (Exception ex) {
			rollbackTransaction();
		}finally {
			closeSession();
		}

		return retorno;
	}

}
