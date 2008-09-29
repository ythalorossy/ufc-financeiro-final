package br.ufc.BO;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.Caixa;
import br.com.ContasReceber;
import br.com.Observacao;
import br.com.Parcela;
import br.ufc.DAO.ContasReceberDAO;
import br.ufc.DAO.DAO;
import br.ufc.TO.ContasReceberTO;
import br.ufc.assembler.ContasReceberAssembler;
import br.ufc.uteis.Status;

public class ContasReceberBO implements BO<ContasReceber> {

	DAO<ContasReceber> dao = new ContasReceberDAO();

	private boolean result = false;

	public boolean delete(ContasReceber e) {
		return false;
	}

	public List<ContasReceber> findAll() {
		return dao.findAll();
	}
	
	public List<ContasReceber> findAll(GregorianCalendar calendar) {
		return ((ContasReceberDAO)dao).findAll(calendar);
	}

	@Override
	public ContasReceber findById(int id) {
		return dao.findById(id);
	}

	public DAO<ContasReceber> getDAO() {
		return dao;
	}

	@Override
	public boolean save(ContasReceber e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(ContasReceber e) {
		return new ContasReceberDAO().update(e);
	}

	public static BO<ContasReceber> getInstance() {
		return new ContasReceberBO();
	}

	public boolean reabrirConta(ContasReceber e) {
		
		final Caixa caixa = ((CaixaBO)CaixaBO.getInstance()).findByIdContaReceber(e.getId());
		if (caixa.getStatus()!= Status.BATIDO){
			/*
			 * Alterando o status do Contas a Receber para Aberto, da parcela para Aberto e excluindo o caixa do banco
			 */
			e.setDataEfetiva(null);
			e.setStatus(Status.ABERTO);
			Parcela parcela = ParcelaBO.getInstance().findById(e.getIdParcela());
			parcela.setStatus(Status.ABERTO);
			if (((ContasReceberDAO)dao).update(e, parcela, caixa)) {
				result = true;
			}
		}

		return result;
	}

	public boolean baixarContasReceber(ContasReceber e,
			String operacaoMonetaria, GregorianCalendar calendar) {

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
		
		final Parcela parcela = ParcelaBO.getInstance().findById(e.getIdParcela());
		parcela.setStatus(Status.PAGO);
			
		final Caixa caixa = retornaCaixa(e);
		if (((ContasReceberDAO)dao).updateBaixa(e, parcela, caixa)){
			result = true;
		}

		return result;
	}
	
	public Caixa retornaCaixa(ContasReceber e){
		final ContasReceberTO to = ContasReceberAssembler.getInstance().entity2EntityTO(e);
		final Caixa caixa = new Caixa();
		caixa.setDataPagamento(e.getDataEfetiva());
		caixa.setDescricaoServico("Nota Fiscal: "+e.getIdNotaFiscal().getNotaFiscal()+", Parcela: "+to.getNumeroParcela());
		caixa.setDoc("NF-"+e.getIdNotaFiscal().getNotaFiscal()+",P-"+to.getNumeroParcela());
		caixa.setIdContasReceber(e);
		caixa.setStatus(Status.AGUARDANDO);
		caixa.setValor(e.getValorEfetivo());
		return caixa;
	}

	@Override
	public boolean delete(List<ContasReceber> list) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean save(List<ContasReceber> contasReceberToSave) {
		return ((ContasReceberDAO)dao).save(contasReceberToSave);

	}

	public ContasReceber findAllByIdParcela(int id) {
		return ((ContasReceberDAO) dao).findAllByIParcela(id);
	}

	public List<ContasReceber> findAllInadimplentes(GregorianCalendar calendar) {
		return ((ContasReceberDAO) dao).findAllInadimplentes(calendar);
	}

	public boolean deleteObservacao(Observacao observacaoToDelete) {
		return ((ContasReceberDAO) dao).deleteObservacao(observacaoToDelete);
		
	}

	public List<ContasReceber> findByNF(String nf) {
		return ((ContasReceberDAO)dao).findByNF(nf);
	}

}
