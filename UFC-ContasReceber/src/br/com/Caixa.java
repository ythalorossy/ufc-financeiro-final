package br.com;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

@Entity
@Table(name="CAIXA")
public class Caixa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7842561003413096386L;
	private int id;
	private ContasReceber idContasReceber;
	private ContasPagar idContasPagar;
	private CaixaEntradaSaida idCaixaEntradaSaida;
	private String doc;
	private String descricaoServico;
	private GregorianCalendar dataPagamento;
	private double valor;
	private int status;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CX_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="CX_CONTAS_RECEBER")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_CONTAS_RECEBER")
	public ContasReceber getIdContasReceber() {
		return idContasReceber;
	}
	public void setIdContasReceber(ContasReceber idContasReceber) {
		this.idContasReceber = idContasReceber;
	}
	
	@ManyToOne
	@JoinColumn(name="CX_CONTAS_PAGAR")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_CONTAS_PAGAR")
	public ContasPagar getIdContasPagar() {
		return idContasPagar;
	}
	public void setIdContasPagar(ContasPagar idContasPagar) {
		this.idContasPagar = idContasPagar;
	}
	
	@ManyToOne
	@JoinColumn(name="CX_CAIXA_ENTRADA_SAIDA")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_CAIXA_ENTRADA_SAIDA")
	public CaixaEntradaSaida getIdCaixaEntradaSaida() {
		return idCaixaEntradaSaida;
	}
	public void setIdCaixaEntradaSaida(CaixaEntradaSaida idCaixaEntradaSaida) {
		this.idCaixaEntradaSaida = idCaixaEntradaSaida;
	}
	@Column(name="CX_DOC")
	public String getDoc() {
		return doc;
	}
	public void setDoc(String doc) {
		this.doc = doc;
	}
	@Column(name="CX_DESCRICAO_SERVICO")
	public String getDescricaoServico() {
		return descricaoServico;
	}
	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
	}
	@Column(name="CX_DATA_PAGAMENTO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(GregorianCalendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	@Column(name="CX_VALOR")
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	@Column(name="CX_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}