package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;

import com.hibernate.HibernateHelper;

public class ItensNotaFiscalDAO implements DAO<ItensNotaFiscal> {
	
	//criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	//Lista de notas fiscais
	private List<ItensNotaFiscal> itensNotaFiscalList = new ArrayList<ItensNotaFiscal>();
	// Objeto Nota Fiscal
	@SuppressWarnings("unused")
	private ItensNotaFiscal itens = new ItensNotaFiscal();
	// Retorno padrão
	private boolean retorno = false;
	
	
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

	@Override
	public boolean delete(ItensNotaFiscal e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ItensNotaFiscal> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItensNotaFiscal findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(ItensNotaFiscal e) {
		startTransaction();
		try{
			hh.save(e);
			retorno = true;
			commitTransaction();
		}catch (Exception ex) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return retorno;
	}

	@Override
	public boolean update(ItensNotaFiscal e) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<ItensNotaFiscal> findAllItensByNf(int idNotaFiscal) {
		startTransaction();
		try {
			itensNotaFiscalList = hh.getSession().createQuery("From ItensNotaFiscal where idNotaFiscal = "+idNotaFiscal).list();
			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally {
			closeSession();
		}
		return itensNotaFiscalList;
	}
	
	@SuppressWarnings("unchecked")
	public double findSum(NotaFiscal notaFiscal) {
		double soma = 0;
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("Select new ItensNotaFiscal(sum(valorTotal)) from ItensNotaFiscal where idNotaFiscal = ?");
			query.setParameter(0, notaFiscal);
			List<ItensNotaFiscal> itens = query.list();
			for (ItensNotaFiscal i:itens){
				soma += i.getValorTotal();
			}
			
			commitTransaction();
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}
		
		return soma;
	}
	public boolean delete(List<ItensNotaFiscal> list) {
		startTransaction();
		try {
			for (ItensNotaFiscal itensNotaFiscal : list) {
				hh.delete(itensNotaFiscal);
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollBackTransaction();
		} finally{
			closeSession();
		}
		return retorno;
		
	}
	@Override
	public ItensNotaFiscal findByObject(ItensNotaFiscal e) {
		// TODO Auto-generated method stub
		return null;
	}

}
