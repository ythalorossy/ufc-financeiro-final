package br.ufc.DAO;

import java.util.List;

import br.com.ContasReceber;
import br.com.Parcela;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class ParcelaDAO implements DAO<Parcela> {

	// criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	// Retorno padrão
	private boolean retorno = false;

	private Parcela parcela = new Parcela();
	
	
	// Métodos que startam a transação, comitam e fecham a sessão
	public void commitTransaction() {
		hh.getSession().getTransaction().commit();
	}

	public void startTransaction() {
		hh.getSession().beginTransaction();
	}

	public void closeSession() {
		hh.getSession().clear();
	}

	public void rollBackTransaction() {
		hh.getSession().getTransaction().rollback();
	}

	public static DAO<Parcela> getInstance() {
		return new ParcelaDAO();
	}

	@Override
	public boolean delete(Parcela e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Parcela> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Parcela findById(int id) {

		try {
			startTransaction();
			parcela = (Parcela) hh.getSession().get(Parcela.class, id);
			commitTransaction();

		} finally {
			closeSession();
		}

		return parcela;
	}

	@Override
	public boolean save(Parcela e) {
		startTransaction();
		try {
			hh.save(e);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		closeSession();
		return retorno;
	}

	public boolean update(Parcela e) {

		try {
			startTransaction();
			hh.getSession().update(e);
			commitTransaction();
			retorno = true;
		} finally {
			closeSession();
		}

		return retorno;
	}

	public List<Parcela> findByIdNfPaga(int id, int status) {
		startTransaction();
		final List<Parcela> parcela = (List<Parcela>) hh.getSession()
				.createQuery(
						"From Parcela where status = " + status
								+ " and idNotaFiscal = " + id).list();
		commitTransaction();
		closeSession();
		return parcela;
	}

	public double findSumParcelasByNF(int idNotaFiscal) {
		startTransaction();
		double soma = 0;
		try {
			soma = (Double) hh.getSession().createSQLQuery("select sum(PAR_VALOR) from financeiro.PARCELA where par_id_nota_fiscal= "+ idNotaFiscal).uniqueResult();
		} catch (Exception e) {
			soma = 0;
		}
		commitTransaction();
		closeSession();
		return soma;
	}

	public List<Parcela> findByIdNf(int id) {
		startTransaction();
		final List<Parcela> parcela = (List<Parcela>) hh.getSession()
				.createQuery(
						"From Parcela where idNotaFiscal = " + id
								+ " order by numeroParcela, dataPagamento").list();
		commitTransaction();
		closeSession();
		return parcela;
	}

	public boolean delete(List<Parcela> parcela) {
		startTransaction();
		try {
			for (Parcela par : parcela) {
				hh.delete(par);
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;

	}

	public boolean save(List<Parcela> parcelas2Save,
			List<ContasReceber> contas2Save) {
		try {
			if (!parcelas2Save.isEmpty()) {
				for (int i = 0; i < parcelas2Save.size(); i++) {
					startTransaction();
					parcelas2Save.get(i).setId(0);
					hh.save(parcelas2Save.get(i));
					commitTransaction();
					
				}
			}
			if (!contas2Save.isEmpty()) {
				for (int i = 0; i < contas2Save.size(); i++) {
					hh.save(contas2Save.get(i));
				}
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	public boolean save(Parcela e, ContasReceber contasReceber) {
		startTransaction();
		try {
			hh.save(e);
			contasReceber.setIdParcela(e.getId());
			hh.save(contasReceber);
			commitTransaction();
			retorno = true;
		} catch (Exception ex) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;

	}

	public boolean delete(List<Parcela> parcela2Delete,
			List<ContasReceber> contasReceber2Delete) {
		startTransaction();
		try {
			if (!parcela2Delete.isEmpty()) {
				for (int i = 0; i < parcela2Delete.size(); i++) {
					hh.delete(parcela2Delete.get(i));
				}
			}
			if (!contasReceber2Delete.isEmpty()) {
				for (int i = 0; i < contasReceber2Delete.size(); i++) {
					hh.delete(contasReceber2Delete.get(i));
				}
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	@Override
	public Parcela findByObject(Parcela e) {
		// TODO Auto-generated method stub
		return null;
	}

}
