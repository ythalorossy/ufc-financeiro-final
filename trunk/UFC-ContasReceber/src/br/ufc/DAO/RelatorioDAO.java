package br.ufc.DAO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;

import br.com.Caixa;
import br.com.ContasPagar;
import br.com.ContasReceber;
import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.PedidoDespesa;
import br.ufc.uteis.Status;

import com.hibernate.HibernateHelper;

@SuppressWarnings("unchecked")
public class RelatorioDAO {

	//criação do objeto hibernate
	private HibernateHelper hh = HibernateHelper.getInstance();
	private List<NotaFiscal> list = new ArrayList<NotaFiscal>();
	private List<ContasReceber> listCR = new ArrayList<ContasReceber>();
	private List<Caixa> listCX = new ArrayList<Caixa>(); 
	private List<ContasPagar> listCP = new ArrayList<ContasPagar>();
	private List<ItensNotaFiscal> listINF = new ArrayList<ItensNotaFiscal>();
	private List<PedidoDespesa> listPD = new ArrayList<PedidoDespesa>();

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

	public static RelatorioDAO getInstance() {
		return new RelatorioDAO();
	}


	public List<NotaFiscal> relatorioNotaFiscal() {
		startTransaction();

		try {
			list = hh.getSession().createQuery("From NotaFiscal").list();
			commitTransaction();
		} catch (Exception e) {
		} finally{
			closeSession();
		}
		return list;
	}
	public List<NotaFiscal> relatorioNotaFiscalUnica(int busca) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
			"From NotaFiscal where id = ?");
			query.setParameter(0, busca);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}
		return list;
	}
	public List<NotaFiscal> relatorioNotaFiscalUnicaCliente(String busca) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
			"From NotaFiscal where idCliente= ?");
			query.setParameter(0, busca);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}
		return list;
	}

	public List<NotaFiscal> relatorioNotaFiscalUnicaPeriodo(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh
			.getSession()
			.createQuery(
					"From NotaFiscal where dataSaida between ? and ? order by dataSaida");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			list = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			closeSession();
		}
		return list;
	}

	public List<ContasReceber> relatorioContasReceber(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
			"From ContasReceber where dataPrevista between ? and ? order by dataPrevista");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			listCR = query.list();
			commitTransaction();
		} catch (Exception e) {

		} finally {
			closeSession();
		}
		return listCR;
	}

	public List<ContasReceber> relatorioContasReceberInadimplentes(GregorianCalendar dataPrevista) {
		startTransaction();
		try {
			Query query = hh
			.getSession()
			.createQuery(
					"From ContasReceber where dataPrevista < ? and status <> ? order by dataPrevista");
			query.setParameter(0, dataPrevista);
			query.setParameter(1, Status.PAGO);
			listCR = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			closeSession();
		}
		return listCR;
	}

	public List<Caixa> relatorioCaixaData(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh
			.getSession()
			.createQuery(
					"From Caixa where dataPagamento between ? and ? order by dataPagamento");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			listCX = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}
		return listCX;
	}
	public List<ContasReceber> relatorioContasReceberCliente(String idCliente) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery(
			"From ContasReceber where idCliente = ? order by dataPrevista");
			query.setParameter(0, idCliente);
			listCR = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			closeSession();
		}

		return listCR;
	}
	public List<ContasPagar> relatorioContasPagar(
			GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From ContasPagar where dataPrevista between ? and ? order by dataPrevista");
			query.setParameter(0, dataInicial);
			query.setParameter(1, dataFinal);
			listCP= query.list();
			commitTransaction();

		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeSession();
		}
		return listCP;
	}
	public List<ContasPagar> relatorioContasPagarDivisao(int idDivisao) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From ContasPagar where idDivisao = ? order by dataPrevista");
			query.setParameter(0, idDivisao);
			listCP = query.list();
			commitTransaction();

		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeSession();
		}
		return listCP;
	}
	public List<ItensNotaFiscal> relatorioLaboratorio(int idLaboratorio, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		try {
			Query query = hh
			.getSession()
			.createQuery(
					"From ItensNotaFiscal where idLaboratorio = ? and data between ? and ?");
			query.setParameter(0, idLaboratorio);
			query.setParameter(1, dataInicial);
			query.setParameter(2, dataFinal);
			listINF = query.list();
			commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			closeSession();
		}
		return listINF;
	}

	public List<PedidoDespesa> relatorioPedidoDespesa(int idPD) {
		startTransaction();
		try {
			Query query = hh.getSession().createQuery("From PedidoDespesa where id = ?");
			query.setParameter(0, idPD);
			listPD = query.list();
			commitTransaction();

		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeSession();
		}
		return listPD;
	}

}
