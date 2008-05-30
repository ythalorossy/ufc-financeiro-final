package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.com.ContasReceber;
import br.com.NotaFiscal;
import br.com.Parcela;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class ParcelaDAO implements DAO<Parcela> {

	// criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	// Retorno padrão
	private boolean retorno = false;

	private Parcela parcela = new Parcela();
	private List<Parcela> list = new ArrayList<Parcela>();
	
	
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

		}catch (Exception e) {
			rollBackTransaction();
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
		}finally {
			closeSession();
		}
		return retorno;
	}

	public boolean update(Parcela e) {

		try {
			startTransaction();
			hh.getSession().update(e);
			commitTransaction();
			retorno = true;
		}catch (Exception ex) {
			rollBackTransaction();
		} finally {
			closeSession();
		}

		return retorno;
	}

	public List<Parcela> findByIdNfPaga(NotaFiscal id, int status) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From Parcela where status = ? and idNotaFiscal = ?");
			query.setParameter(0, status);
			query.setParameter(1, id);
			list = query.list(); 
			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}

	public double findSumParcelasByNF(NotaFiscal idNotaFiscal) {
		double soma = 0;
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("Select new Parcela(sum(valor)) " +
					"from Parcela where idNotaFiscal = ?");
			query.setParameter(0, idNotaFiscal);
			List<Parcela> itens = query.list();
			for (Parcela i:itens){
				soma += i.getValor();
			}
			
			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}
		
		return soma;
	}

	public List<Parcela> findByIdNf(NotaFiscal id) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
							"From Parcela where idNotaFiscal = ? order by numeroParcela, dataPagamento");
			query.setParameter(0, id);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
			
		return list;
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
