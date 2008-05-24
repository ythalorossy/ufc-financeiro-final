package br.ufc.DAO;

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
		final List<NotaFiscal> list = hh.getSession().createQuery("From NotaFiscal").list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<NotaFiscal> relatorioNotaFiscalUnica(int busca) {
		startTransaction();
		Query query = hh.getSession().createQuery("From NotaFiscal where id = ?");
		query.setParameter(0, busca);
		final List<NotaFiscal> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<NotaFiscal> relatorioNotaFiscalUnicaCliente(String busca) {
		startTransaction();
		Query query = hh.getSession().createQuery("From NotaFiscal where idCliente= ?");
		query.setParameter(0, busca);
		final List<NotaFiscal> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public List<NotaFiscal> relatorioNotaFiscalUnicaPeriodo(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		Query query = hh.getSession().createQuery("From NotaFiscal where dataSaida between ? and ? order by dataSaida");
		query.setParameter(0, dataInicial);
		query.setParameter(1, dataFinal);
		final List<NotaFiscal> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public List<ContasReceber> relatorioContasReceber(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ContasReceber where dataPrevista between ? and ? order by dataPrevista");
		query.setParameter(0, dataInicial);
		query.setParameter(1, dataFinal);
		final List<ContasReceber> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public List<ContasReceber> relatorioContasReceberInadimplentes(GregorianCalendar dataPrevista) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ContasReceber where dataPrevista < ? and status <> ? order by dataPrevista");
		query.setParameter(0, dataPrevista);
		query.setParameter(1, Status.PAGO);
		final List<ContasReceber> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	
	public List<Caixa> relatorioCaixaData(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		Query query = hh.getSession().createQuery("From Caixa where dataPagamento between ? and ? order by dataPagamento");
		query.setParameter(0, dataInicial);
		query.setParameter(1, dataFinal);
		final List<Caixa> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<ContasReceber> relatorioContasReceberCliente(String idCliente) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ContasReceber where idCliente = ? order by dataPrevista");
		query.setParameter(0, idCliente);
		final List<ContasReceber> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<ContasPagar> relatorioContasPagar(
			GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ContasPagar where dataPrevista between ? and ? order by dataPrevista");
		query.setParameter(0, dataInicial);
		query.setParameter(1, dataFinal);
		final List<ContasPagar> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<ContasPagar> relatorioContasPagarDivisao(int idDivisao) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ContasPagar where idDivisao = ? order by dataPrevista");
		query.setParameter(0, idDivisao);
		final List<ContasPagar> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}
	public List<ItensNotaFiscal> relatorioLaboratorio(int idLaboratorio, GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		startTransaction();
		Query query = hh.getSession().createQuery("From ItensNotaFiscal where idLaboratorio = ? and data between ? and ?");
		query.setParameter(0, idLaboratorio);
		query.setParameter(1, dataInicial);
		query.setParameter(2, dataFinal);
		final List<ItensNotaFiscal> list = query.list();
		commitTransaction();
		closeSession();
		return list;	
	}
	
	public List<PedidoDespesa> relatorioPedidoDespesa(int idPD) {
		startTransaction();
		Query query = hh.getSession().createQuery("From PedidoDespesa where id = ?");
		query.setParameter(0, idPD);
		final List<PedidoDespesa> list = query.list();
		commitTransaction();
		closeSession();
		return list;
	}

}
