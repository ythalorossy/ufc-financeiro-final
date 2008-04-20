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
@Table(name="CONTAS_PAGAR")
public class ContasPagar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6533594559267158562L;
	private int id;
	private int idDivisao;
	private String fornecedor;
	private FormasPagamento idFormasPagamento;
	private int idNotaFiscal;
	private int idPedidoDespesa;
	private int idParcela;
	private double valorPrevista;
	private GregorianCalendar dataPrevista;
	private double valorMonetario;
	private double valorEfetivo;
	private GregorianCalendar dataEfetiva;
	private String observacao;
	private int status;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CTP_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="CTP_ID_DIVISAO")
	public int getIdDivisao() {
		return idDivisao;
	}
	public void setIdDivisao(int idCliente) {
		this.idDivisao = idCliente;
	}
	@ManyToOne
	@JoinColumn(name="CTP_ID_FORMA_PAGAMENTO")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_FORMA_PAGAMENTO")
	public FormasPagamento getIdFormasPagamento() {
		return idFormasPagamento;
	}
	public void setIdFormasPagamento(FormasPagamento idFormasPagamento) {
		this.idFormasPagamento = idFormasPagamento;
	}

	@Column(name="CTP_ID_NOTA_FISCAL")
	public int getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(int idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	@Column(name="CTP_PARCELA")
	public int getIdParcela() {
		return idParcela;
	}
	public void setIdParcela(int idParcela) {
		this.idParcela = idParcela;
	}
	@Column(name="CTP_VALOR_PREVISTO")
	public double getValorPrevista() {
		return valorPrevista;
	}
	public void setValorPrevista(double valorPrevista) {
		this.valorPrevista = valorPrevista;
	}
	@Column(name="CTP_DATA_PREVISTA")
	@Type(type="calendar_date")
	public GregorianCalendar getDataPrevista() {
		return dataPrevista;
	}
	public void setDataPrevista(GregorianCalendar dataPrevista) {
		this.dataPrevista = dataPrevista;
	}
	@Column(name="CTP_VALOR_MONETARIO")
	public double getValorMonetario() {
		return valorMonetario;
	}
	public void setValorMonetario(double valorMonetario) {
		this.valorMonetario = valorMonetario;
	}
	@Column(name="CTP_VALOR_EFETIVO")
	public double getValorEfetivo() {
		return valorEfetivo;
	}
	public void setValorEfetivo(double valorEfetivo) {
		this.valorEfetivo = valorEfetivo;
	}
	@Column(name="CTP_DATA_EFETIVO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataEfetiva() {
		return dataEfetiva;
	}
	public void setDataEfetiva(GregorianCalendar dataEfetiva) {
		this.dataEfetiva = dataEfetiva;
	}
	@Column(name="CTP_OBSERVACAO")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	@Column(name="CTP_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="CTP_FORNECEDOR")
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	@Column(name="CPT_ID_PEDIDO_DESPESA")
	public int getIdPedidoDespesa() {
		return idPedidoDespesa;
	}
	public void setIdPedidoDespesa(int idPedidoDespesa) {
		this.idPedidoDespesa = idPedidoDespesa;
	}
}