package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.ContasReceber;
import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.ufc.uteis.Status;

import com.converte.ConverteData;
import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class NotaFiscalDAO implements DAO<NotaFiscal> {
	
	//criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	//Lista de notas fiscais
	private List<NotaFiscal> list = new ArrayList<NotaFiscal>();
	// Objeto Nota Fiscal
	private NotaFiscal nf = new NotaFiscal();
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
	public void rollbackTransaction(){
		hh.getSession().getTransaction().rollback();
	}

	
	
	public List<NotaFiscal> findAll() {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From NotaFiscal order by dataSaida");
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}

	public NotaFiscal findById(int id) {
		startTransaction();
		try {
			nf = (NotaFiscal) hh.getSession().get(NotaFiscal.class, id);
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return nf;
	}

	public boolean save(NotaFiscal notaFiscal) {
		startTransaction();
		try{
			hh.getSession().saveOrUpdate(notaFiscal);
			retorno = true;
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		}finally {
			closeSession();
		}
			
		return retorno;
	}

	@Override
	public boolean update(NotaFiscal notaFiscal) {
		startTransaction();
		try{
			hh.getSession().update(notaFiscal);
			retorno = true;
			commitTransaction();
		}catch (Exception e) {
			rollbackTransaction();
		}finally {
			closeSession();
		}
		return retorno;
	}

	
	public List<NotaFiscal> findByNf(String notaFiscal) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
					"From NotaFiscal where notaFiscal = '" + notaFiscal + "'");
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}

	public boolean delete(NotaFiscal notaFiscal) {
		try{
			startTransaction();
			hh.delete(notaFiscal);
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollbackTransaction();
		}finally {
			closeSession();
		}
		return retorno;
	}
	
	
	public boolean update(NotaFiscal notaFiscal, List<Parcela> parcelas,List<ItensNotaFiscal> itens, 
				List<ContasReceber> contasReceber) {
		try{
			startTransaction();
			hh.getSession().update(notaFiscal);
			if (!parcelas.isEmpty()){	
				for (int i = 0; i < parcelas.size(); i++){
					hh.getSession().update(parcelas.get(0));
				}
			}
			if (!itens.isEmpty()){
				for (int i = 0; i < itens.size(); i++){
					hh.getSession().update(itens.get(0));
				}
			}
			if(!contasReceber.isEmpty()){
				for(int i = 0; i < contasReceber.size(); i++){
					hh.delete(contasReceber.get(i));
				}
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollbackTransaction();
		}finally {
			closeSession();
		}
			
	return retorno;
	}
	
	public NotaFiscal findByNF(String notaFiscal) {
		startTransaction();
		try {
			nf = (NotaFiscal) hh.getSession().createQuery("From NotaFiscal where notaFiscal = '"+notaFiscal+"'").uniqueResult();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		}finally{
			closeSession();
		}
		return nf;
	}
	
	public List<NotaFiscal> findByNF(NotaFiscal notaFiscal) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From NotaFiscal where notaFiscal = ?");
			query.setParameter(0, notaFiscal.getNotaFiscal());
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		}finally{
			closeSession();
		}
		return list;
	}
	
	public boolean reabrirNotaFiscal(NotaFiscal notaFiscal, List<Parcela> parcelas,List<ItensNotaFiscal> itens) {
		try{
			startTransaction();
			hh.getSession().update(notaFiscal);
			if (!parcelas.isEmpty()){	
				for (int i = 0; i < parcelas.size(); i++){
					hh.getSession().update(parcelas.get(0));
				}
			}
			if (!itens.isEmpty()){
				for (int i = 0; i < itens.size(); i++){
					hh.getSession().update(itens.get(0));
				}
			}
			commitTransaction();
			retorno = true;
		} catch (Exception e) {
			rollbackTransaction();
		}finally {
			closeSession();
		}
		return retorno;
	}
	@Override
	public NotaFiscal findByObject(NotaFiscal e) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<NotaFiscal> findNotas30(GregorianCalendar calendar) {
		System.out.println(ConverteData.retornaData(calendar));
		try {
			startTransaction();
			Query query = hh.getSession().createQuery(
					"From NotaFiscal where dataSaida >= ? and tipoNota<>? order by dataSaida");
			query.setParameter(0, calendar);
			query.setParameter(1, Status.NOTA_NAO_CONTABILIZADA);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	public List<NotaFiscal> findByDay(GregorianCalendar dataInicial,GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
							"From NotaFiscal where dataSaida between ? and ? order by dataSaida");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		} finally {
			closeSession();
		}
		return list;
	}
	public List<NotaFiscal> findByNumeroProcesso(String numeroProcesso) {
		List<NotaFiscal> list = new ArrayList<NotaFiscal>();
		try {
			startTransaction();
			Query query = hh.getSession().createQuery("From NotaFiscal where numeroProcesso = ?");
			query.setParameter(0, numeroProcesso);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
		}finally{
			closeSession();
		}
		return list;
	}

}
