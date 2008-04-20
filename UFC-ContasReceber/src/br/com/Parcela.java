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
@Table(name="PARCELA")
public class Parcela implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8163720835598689956L;
	private int id;
	private int numeroParcela;
	private NotaFiscal idNotaFiscal;
	private String idCliente;
	private GregorianCalendar dataPagamento;
	private double valor;
	private int status;
	
	
	public Parcela(double valor) {
		this.valor = valor;
	}
	public Parcela() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PAR_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="PAR_ID_NOTA_FISCAL")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_NOTA_FISCAL")
	public NotaFiscal getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(NotaFiscal idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	
	@Column(name="PAR_ID_CLIENTE")
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	
	@Column(name="PAR_DATA_PAGAMENTO")
	@Type(type = "calendar_date")
	public GregorianCalendar getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(GregorianCalendar dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	@Column(name="PAR_VALOR")
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	@Column(name="PAR_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="PAR_NUMERO_PARCELA")
	public int getNumeroParcela() {
		return numeroParcela;
	}
	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}
}