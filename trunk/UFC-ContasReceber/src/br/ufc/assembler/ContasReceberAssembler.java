package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.ContasReceber;
import br.com.FormasPagamento;
import br.com.NotaFiscal;
import br.com.Observacao;
import br.com.Parcela;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.ContasReceberBO;
import br.ufc.BO.FormasPagamentoBO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.BO.ParcelaBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.TO.ContasReceberTO;

import com.Auxiliar.Clientes;
import com.converte.ConverteData;

public class ContasReceberAssembler implements
		ASSEMBLER<ContasReceber, ContasReceberTO> {

	public static ContasReceberAssembler getInstance() {
		return new ContasReceberAssembler();
	}
	
	public ContasReceberTO entity2EntityTO(ContasReceber entity) {
		final ContasReceberTO to = new ContasReceberTO();
		
		to.setId(String.valueOf(entity.getId()));
		try {
			to.setDataEfetiva(ConverteData.retornaData(entity.getDataEfetiva()));
		} catch (Exception e) {
			to.setDataEfetiva("");
		}
		
		to.setDataPrevista(ConverteData.retornaData(entity.getDataPrevista()));

		to.setIdCliente(String.valueOf(entity.getIdCliente()));
		to.setNomeCliente(retornaCliente(entity.getIdCliente()));
		try {
			to.setIdFormasPagamento(retornaFormaPagamento(entity.getIdFormasPagamento()));
		} catch (Exception e) {
			to.setIdFormasPagamento("");
		}
		
		to.setIdNotaFiscal(String.valueOf(entity.getIdNotaFiscal().getId()));
		to.setNumeroNotaFiscal(retornaNF(entity.getIdNotaFiscal()));
		to.setIdParcela(String.valueOf(entity.getIdParcela()));
		to.setNumeroParcela(retornaParcela(entity.getIdParcela()));
		to.setObservacao(ObservacaoAssembler.getInstance().entity2EntityTO(entity.getObservacao()));
		to.setStatus(String.valueOf(entity.getStatus()));
		to.setValorEfetivo(ConverteNumero.converteNumero(entity.getValorEfetivo()));
		to.setValorMonetario(ConverteNumero.converteNumero(entity.getValorMonetario()));
		to.setValorPrevista(ConverteNumero.converteNumero(entity.getValorPrevista()));
		System.out.println(to.getValorPrevista());
		return to;

	}

	public List<ContasReceberTO> entity2EntityTO(List<ContasReceber> entity) {
		final List<ContasReceberTO> listTO = new ArrayList<ContasReceberTO>();
		for (ContasReceber contasReceber : entity) {
			listTO.add(entity2EntityTO(contasReceber));
		}
		return listTO;

	}

	public ContasReceber entityTO2Entity(ContasReceberTO to) {
		final ContasReceber entity = new ContasReceber();
		
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
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
		
		entity.setIdCliente(to.getIdCliente());
		
		entity.setIdFormasPagamento(retornaFormaPagamento(to.getIdFormasPagamento()));
		if(entity.getIdFormasPagamento()== null){
			try {
				entity.setIdFormasPagamento(findFormaPagamento(to.getIdFormasPagamento()));
			} catch (Exception e) {
				entity.setIdFormasPagamento(null);
			}
			
		}
		entity.setIdNotaFiscal(retornaNF(to.getIdNotaFiscal()));
		if(entity.getIdNotaFiscal()== null){
			entity.setIdNotaFiscal(findNF(to.getIdNotaFiscal()));
		}
		try {
			entity.setIdParcela(Integer.parseInt(to.getIdParcela()));
		} catch (Exception e) {
		}
		entity.setObservacao(returnObservacao(entity.getId()));
		entity.setStatus(Integer.parseInt(to.getStatus()));
		try {
			entity.setValorEfetivo(ConverteNumero.converteNumero(to.getValorEfetivo()));
			entity.setValorMonetario(ConverteNumero.converteNumero(to.getValorMonetario()));
			entity.setValorPrevista(ConverteNumero.converteNumero(to.getValorPrevista()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return entity;

	}

	public List<ContasReceber> entityTO2Entity(List<ContasReceberTO> to) {
		final List<ContasReceber> listEntity = new ArrayList<ContasReceber>();
		for (ContasReceberTO contasReceberTO : to) {
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
	public String retornaCliente(String idCliente){
		final Clientes cliente = new ClientesDAO().findById(idCliente);
		return cliente.getNome();
	}
	
	public List<Observacao> returnObservacao(int idContasReceber){
		final ContasReceber cr = ContasReceberBO.getInstance().findById(idContasReceber);
		final List<Observacao> list = cr.getObservacao();
		return list;
	}
	
}
