package br.ufc.BO;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.Caixa;
import br.com.ContasPagar;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.com.PedidoDespesa;
import br.ufc.DAO.CaixaDAO;
import br.ufc.DAO.ContasPagarDAO;
import br.ufc.DAO.DAO;
import br.ufc.DAO.ParcelaDAO;
import br.ufc.uteis.Status;

public class ContasPagarBO implements BO<ContasPagar> {

	DAO<ContasPagar> dao = new ContasPagarDAO();

	private boolean result = false;

	public boolean delete(ContasPagar e) {
		return false;
	}

	public List<ContasPagar> findAll() {
		return dao.findAll();
	}
	
	public List<ContasPagar> findAll(GregorianCalendar calendar) {
		return ((ContasPagarDAO)dao).findAll(calendar);
	}

	@Override
	public ContasPagar findById(int id) {
		return dao.findById(id);
	}

	public DAO<ContasPagar> getDAO() {
		return dao;
	}

	@Override
	public boolean save(ContasPagar e) {
		return dao.save(e);
	}

	@Override
	public boolean update(ContasPagar e) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ContasPagar> findContasPagarDia(GregorianCalendar calendar) {

		return ((ContasPagarDAO) dao).findContasPagarDia(calendar);
	}

	public static BO<ContasPagar> getInstance() {
		return new ContasPagarBO();
	}

	public boolean reabrirConta(ContasPagar e) {
		
		final Caixa caixa = ((CaixaBO)CaixaBO.getInstance()).findByIdContaPagar(e.getId());
		if (caixa.getStatus()!= Status.BATIDO){
			e.setValorEfetivo(0);
			/*
			 * Alterando o status do Contas a Pagar para Aberto, da parcela para Aberto e excluindo o caixa do banco
			 */
			e.setStatus(Status.ABERTO);
			if (e.getIdNotaFiscal() !=0){
			
				
				final Parcela parcela = ParcelaBO.getInstance().findById(e.getIdParcela());
				parcela.setStatus(Status.ABERTO);
				if (((ContasPagarDAO)dao).update(e, parcela, caixa)) {
					result = true;
				}
			}
			
			if (e.getIdPedidoDespesa() != 0){
				final PedidoDespesa pd = PedidoDespesaBO.getInstance().findById(e.getIdPedidoDespesa());
				pd.setStatus(Status.CONFIRMADO);
				if(((ContasPagarDAO)dao).update(e, pd, caixa)){
					result = true;
				}
			}
		}

		return result;
	}

	public boolean baixarContasPagar(ContasPagar e,
			String operacaoMonetaria, GregorianCalendar calendar) {

		final CaixaDAO caixaDAO = new CaixaDAO();
		
		switch (Integer.parseInt(operacaoMonetaria)) {
			case Status.DESCONTO:
				e.setValorEfetivo(e.getValorPrevista() - e.getValorMonetario());
				break;
			case Status.JUROS:
				e.setValorEfetivo(e.getValorPrevista() + e.getValorMonetario());
				break;
	
			default:
				e.setValorEfetivo(e.getValorPrevista());
				break;
		}
		
		/*
		 * Muda Status para Pago
		 */
		e.setStatus(Status.PAGO);
		
		/*
		 * Muda Data de Pagamento
		 */
		e.setDataEfetiva(calendar);
		e.setValorEfetivo(e.getValorEfetivo()*-1);
		
		/*
		 * Atualiza a Conta a Receber
		 */
		if (((ContasPagarDAO) dao).update(e)){
			
			if(e.getIdNotaFiscal() != 0){

				/*
				 * Atualiza o status da parcela para PAGO
				 */
				Parcela parcela = ParcelaBO.getInstance().findById(e.getIdParcela());
				parcela.setStatus(Status.PAGO);

				if (ParcelaDAO.getInstance().update(parcela)){
					if (caixaDAO.save(retornaCaixaNotaFiscal(e))){
						result = true;
					}
				}
			}
			if (e.getIdPedidoDespesa()!=0){
				final PedidoDespesa pd = PedidoDespesaBO.getInstance().findById(e.getIdPedidoDespesa());
				pd.setStatus(Status.PAGO);
				if (PedidoDespesaBO.getInstance().update(pd)){
					if(caixaDAO.save(retornaCaixaPedidoDespesa(e))){
						result = true;
					}
				}
			}
		}

		return result;
	}
	
	public Caixa retornaCaixaNotaFiscal(ContasPagar e){
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(e.getIdNotaFiscal());
		Caixa caixa = new Caixa();
		caixa.setDataPagamento(e.getDataEfetiva());
		caixa.setDescricaoServico("Nota Fiscal: "+notaFiscal.getNotaFiscal()+", Parcela: "+e.getIdParcela());
		caixa.setDoc("NF-"+notaFiscal.getNotaFiscal()+",P-"+e.getIdParcela());
		caixa.setIdContasPagar(e);
		caixa.setStatus(Status.AGUARDANDO);
		caixa.setValor(e.getValorEfetivo());
		return caixa;
	}

	public Caixa retornaCaixaPedidoDespesa(ContasPagar e){
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(e.getIdPedidoDespesa());
		Caixa caixa = new Caixa();
		caixa.setDataPagamento(e.getDataEfetiva());
		caixa.setDescricaoServico("Pedido de Despesa: "+pedidoDespesa.getNumeroPD());
		caixa.setDoc("PD-"+pedidoDespesa.getNumeroPD());
		caixa.setIdContasPagar(e);
		caixa.setStatus(Status.AGUARDANDO);
		caixa.setValor(e.getValorEfetivo());
		return caixa;
	}

	@Override
	public boolean delete(List<ContasPagar> list) {
		return ((ContasPagarDAO)dao).delete(list);
	}

	public boolean save(List<ContasPagar> contasReceberToSave) {
		return ((ContasPagarDAO)dao).save(contasReceberToSave);

	}

	public ContasPagar findAllByIdParcela(int id) {
		return ((ContasPagarDAO) dao).findAllByIParcela(id);
	}

	public List<ContasPagar> findByIdPD(int id) {
		return ((ContasPagarDAO) dao).findfindByIdPD(id);
	}

}
