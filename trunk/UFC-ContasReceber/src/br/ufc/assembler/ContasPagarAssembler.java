package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.ContasPagar;
import br.com.FormasPagamento;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.com.PedidoDespesa;
import br.ufc.BO.FormasPagamentoBO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.BO.ParcelaBO;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.TO.ContasPagarTO;
import br.ufc.uteis.Status;

import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

public class ContasPagarAssembler implements ASSEMBLER<ContasPagar, ContasPagarTO> {

	public static ContasPagarAssembler getInstance() {
		return new ContasPagarAssembler();
	}
	
	public ContasPagarTO entity2EntityTO(ContasPagar entity) {
		final ContasPagarTO to = new ContasPagarTO();
		
		to.setId(String.valueOf(entity.getId()));
		try {
			to.setDataEfetiva(ConverteData.retornaData(entity.getDataEfetiva()));
		} catch (Exception e) {
			to.setDataEfetiva("");
		}
		
		to.setDataPrevista(ConverteData.retornaData(entity.getDataPrevista()));

		to.setIdDivisao(String.valueOf(entity.getIdDivisao()));
		to.setNomeDivisao(new DivisaoDAO().findById(entity.getIdDivisao()).getNome());
		try {
			to.setIdFormasPagamento(retornaFormaPagamento(entity.getIdFormasPagamento()));
		} catch (Exception e) {
			to.setIdFormasPagamento("");
		}
		
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(entity.getIdNotaFiscal());
		try {
			to.setIdNotaFiscal(notaFiscal.getNotaFiscal());
			to.setNumeroNF(notaFiscal.getNotaFiscal());
		} catch (Exception e) {
			to.setIdNotaFiscal("");
		}
		
		to.setIdParcela(String.valueOf(entity.getIdParcela()));
		try {
			to.setNumeroParcela(retornaParcela(entity.getIdParcela()));
		} catch (Exception e) {
			to.setNumeroParcela("");
		}
		
		to.setObservacao(entity.getObservacao());
		to.setStatus(Status.retornaTipo(entity.getStatus()));
		to.setValorEfetivo(ConverteNumero.converteNumero(entity.getValorEfetivo()));
		to.setValorMonetario(ConverteNumero.converteNumero(entity.getValorMonetario()));
		to.setValorPrevista(ConverteNumero.converteNumero(entity.getValorPrevista()));
		to.setFornecedor(entity.getFornecedor());
		
		final PedidoDespesa pd = PedidoDespesaBO.getInstance().findById(entity.getIdPedidoDespesa());
		try {
			to.setIdPedidoDespesa(String.valueOf(pd.getId()));
			to.setNumeroPD(pd.getNumeroPD());
		} catch (Exception e) {
			to.setIdPedidoDespesa("");
		}
		
		return to;

	}

	public List<ContasPagarTO> entity2EntityTO(List<ContasPagar> entity) {
		final List<ContasPagarTO> listTO = new ArrayList<ContasPagarTO>();
		for (ContasPagar contasPagar : entity) {
			listTO.add(entity2EntityTO(contasPagar));
		}
		return listTO;

	}

	public ContasPagar entityTO2Entity(ContasPagarTO to) {
		final ContasPagar entity = new ContasPagar();
		
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
		}
		try {
			entity.setIdPedidoDespesa(Integer.parseInt(to.getIdPedidoDespesa()));
		} catch (Exception e) {
			entity.setIdPedidoDespesa(0);
		}
		try {
			entity.setDataEfetiva(ConverteData.retornaData(to.getDataEfetiva()));
		} catch (Exception ex) {
			entity.setDataEfetiva(null);
		}
		try {
			entity.setDataPrevista(ConverteData.retornaData(to.getDataPrevista()));
		} catch (Exception ex) {
			entity.setDataPrevista(null);
		}
		
		entity.setIdDivisao(Integer.parseInt(to.getIdDivisao()));
		
		entity.setIdFormasPagamento(retornaFormaPagamento(to.getIdFormasPagamento()));
		if(entity.getIdFormasPagamento()== null){
			try {
				entity.setIdFormasPagamento(findFormaPagamento(to.getIdFormasPagamento()));
			} catch (Exception e) {
				entity.setIdFormasPagamento(null);
			}
			
		}
		try {
			final NotaFiscal notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findByNF(to.getIdNotaFiscal());
			entity.setIdNotaFiscal(notaFiscal.getId());
		} catch (Exception e) {
			entity.setIdNotaFiscal(0);
		}
		
		try {
			entity.setIdParcela(Integer.parseInt(to.getIdParcela()));
		} catch (Exception e) {
		}
		entity.setObservacao(to.getObservacao());
		entity.setStatus(Status.retornaTipo(to.getStatus()));
		try {
			entity.setValorEfetivo(ConverteNumero.converteNumero(to.getValorEfetivo()));
		}catch (Exception e) {
			// TODO: handle exception
		}
		try {
			entity.setValorMonetario(ConverteNumero.converteNumero(to.getValorMonetario()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try{
			entity.setValorPrevista(ConverteNumero.converteNumero(to.getValorPrevista()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		entity.setFornecedor(to.getFornecedor());
		
		return entity;

	}

	public List<ContasPagar> entityTO2Entity(List<ContasPagarTO> to) {
		final List<ContasPagar> listEntity = new ArrayList<ContasPagar>();
		for (ContasPagarTO contasReceberTO : to) {
			listEntity.add(entityTO2Entity(contasReceberTO));
		}
		return listEntity;

	}
	
	/*
	 * Alguns Metodos Auxiliares para manutencao dentro deste Assembler
	 */
	public FormasPagamento findFormaPagamento(String idFormaPagamento){
		final int id = Integer.parseInt(idFormaPagamento);
		final FormasPagamento formasPagamento = new FormasPagamentoBO().findById(id);
		return formasPagamento;
	}
	
	public FormasPagamento retornaFormaPagamento(String idFormaPagamento){
		final FormasPagamento formaPagamento = new FormasPagamentoBO().findByFormaPagamento(idFormaPagamento);
		return formaPagamento;
	}
	
	public String retornaFormaPagamento(FormasPagamento idFormasPagamento){
		final int id = idFormasPagamento.getId();
		final FormasPagamento formasPagamento = new FormasPagamentoBO().findById(id);
		return formasPagamento.getFormaPagamento();
	}
	
	public NotaFiscal findNF(String idNotaFiscal){
		final int id = Integer.parseInt(idNotaFiscal);
		final NotaFiscal notaFiscal = new NotaFiscalBO().findById(id);
		return notaFiscal;
	}
	
	public NotaFiscal retornaNF(String idNotaFiscal){
		final NotaFiscal notaFiscal = new NotaFiscalBO().findByNF(idNotaFiscal);
		return notaFiscal;
	}
	
	public String retornaNF(NotaFiscal idNotaFiscal){
		final int id = idNotaFiscal.getId();
		final NotaFiscal notaFiscal = new NotaFiscalBO().findById(id);
		return notaFiscal.getNotaFiscal();
	}
	
	public String retornaParcela(int id){
		final Parcela parcela = ParcelaBO.getInstance().findById(id);
		return String.valueOf(parcela.getNumeroParcela());
	}
	

	
}
