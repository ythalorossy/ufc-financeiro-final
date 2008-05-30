package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.Caixa;
import br.com.ContasReceber;
import br.com.Observacao;
import br.com.Parcela;
import br.ufc.uteis.Status;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class ContasReceberDAO implements DAO<ContasReceber> {

	// criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	private ContasReceber contasReceber = new ContasReceber();
	private List<ContasReceber> list = new ArrayList<ContasReceber>();
	
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

	public List<ContasReceber> findAll() {
		List<ContasReceber> listContasReceber = new ArrayList<ContasReceber>();
		try {
			startTransaction();
			listContasReceber = hh.getSession().createQuery("from ContasReceber where status=" + Status.ABERTO+" order by dataPrevista").list();
			commitTransaction();
		} finally {
			closeSession();
		}

		return listContasReceber;
	}
	
	public List<ContasReceber> findAll(GregorianCalendar calendar) {
		List<ContasReceber> listContasReceber = new ArrayList<ContasReceber>();
		try {
			startTransaction();
			Query query = hh.getSession().createQuery("from ContasReceber where status=? And dataPrevista=? order by dataPrevista");
			query.setParameter(0, Status.ABERTO);
			query.setParameter(1, calendar);
			listContasReceber = query.list();
			commitTransaction();
		} finally {
			closeSession();
		}

		return listContasReceber;
	}
	

	@Override
	public ContasReceber findById(int id) {
		startTransaction();
		try {
			contasReceber = (ContasReceber)hh.getSession().get(ContasReceber.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally{
			closeSession();
		}
		return contasReceber;
	}

	@Override
	public boolean save(ContasReceber e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(ContasReceber e, Parcela parcela, Caixa caixa) {
		startTransaction();
		try {
			hh.getSession().update(e);
			hh.getSession().update(parcela);
			if (caixa != null){
				hh.delete(caixa);
			}
			commitTransaction();
			retorno = true;
		}
		catch (Exception ex) {
			rollbackTransaction();

		}finally {
			closeSession();
		}

		return retorno;
	}
	
	public boolean updateBaixa(ContasReceber e, Parcela parcela, Caixa caixa) {
		startTransaction();
		try {
			hh.getSession().update(e);
			hh.getSession().update(parcela);
			if (caixa != null){
				hh.save(caixa);
			}
			commitTransaction();
			retorno = true;
		}
		catch (Exception ex) {
			rollbackTransaction();

		}finally {
			closeSession();
		}

		return retorno;
	}

	@Override
	public boolean delete(ContasReceber e) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ContasReceber> findAllContasByNf(int id) {
		startTransaction();
		try {
			list = hh.getSession().createQuery(
					"From ContasReceber where idNotaFiscal = " + id).list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}

	public ContasReceber findAllByIParcela(int id) {
		startTransaction();
		try {
			contasReceber = (ContasReceber) hh.getSession()
			.createQuery("From ContasReceber where idParcela = " + id)
			.uniqueResult();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return contasReceber;
	}

	@Override
	public ContasReceber findByObject(ContasReceber e) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean save(List<ContasReceber> contasReceberToSave) {
		startTransaction();
		try {
			for (int i = 0; i < contasReceberToSave.size(); i++) {
				hh.save(contasReceberToSave.get(i));
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
	public boolean update(ContasReceber e) {
		startTransaction();
		try {
			hh.getSession().update(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollbackTransaction();
		}finally{
			closeSession();
		}
		return retorno;
	}

	public List<ContasReceber> findAllInadimplentes(GregorianCalendar calendar) {
		List<ContasReceber> listContasReceber = new ArrayList<ContasReceber>();
		try {
			startTransaction();
			Query query = hh.getSession().createQuery("from ContasReceber where status <> ? And dataEfetiva < ?");
			query.setParameter(0, Status.PAGO);
			query.setParameter(1, calendar);
			listContasReceber = query.list();
			commitTransaction();
		} finally {
			closeSession();
		}

		return listContasReceber;	}

	public boolean deleteObservacao(Observacao observacaoToDelete) {
		startTransaction();
		try {
			hh.delete(observacaoToDelete);
			commitTransaction();
			retorno = true;
			
		} catch (Exception e) {
			rollbackTransaction();
		} finally{
			closeSession();
		}
		return retorno;
	}

}
