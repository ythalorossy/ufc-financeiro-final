package br.ufc.DAO;

import java.util.ArrayList;
import java.util.List;

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
			System.out.println(ex.getMessage());
		}
		closeSession();
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
		itensNotaFiscalList = hh.getSession().createQuery("From ItensNotaFiscal where idNotaFiscal = "+idNotaFiscal).list();
		commitTransaction();
		closeSession();
		return itensNotaFiscalList;
	}
	
	public double findSum(NotaFiscal notaFiscal) {
		startTransaction();
		final double soma = (Double) hh.getSession().createSQLQuery(
				"Select sum(INF_ID_VALOR_TOTAL_ITEM) from financeiro.ITEMS_NOTA_FISCAL where INF_NOTA_FISCAL = "
				+notaFiscal.getId()).uniqueResult();
		commitTransaction();
		closeSession();
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
