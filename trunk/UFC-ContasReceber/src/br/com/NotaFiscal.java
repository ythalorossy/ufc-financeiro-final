package br.com;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="NOTA_FISCAL")
public class NotaFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8320860492391238667L;
	private int id;
	private String idCliente;
	private String numeroProcesso;
	private String numeroContrato;
	private String notaFiscal;
	private GregorianCalendar dataSaida;
	private double valorNota;
	private int tipoNota; //nota contabilizada ou não contabilizada
	private int status; //Nota ativa ou cancelada
	private String cancelamento;
	private Double desconto;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NFS_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="NFS_ID_CLIENTE")
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	@Column(name="NFS_NOTA_FISCAL")
	public String getNotaFiscal() {
		return notaFiscal;
	}
	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	@Column(name="NFS_DATA_SAIDA")
	@Type(type="calendar_date")
	@OrderBy(clause="NFS_DATA_SAIDA")
	public GregorianCalendar getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(GregorianCalendar data) {
		this.dataSaida = data;
	}
	@Column(name="NFS_VALOR_TOTAL")
	public double getValorNota() {
		return valorNota;
	}
	public void setValorNota(double valorNota) {
		this.valorNota = valorNota;
	}
	@Column(name="NFS_TIPO")
	public int getTipoNota() {
		return tipoNota;
	}
	public void setTipoNota(int tipoNota) {
		this.tipoNota = tipoNota;
	}
	@Column(name="NFS_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="NFS_NUMERO_PROCESSO")
	public String getNumeroProcesso() {
		return numeroProcesso;
	}
	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
	
	@Column(name="NFS_NUMERO_CONTRATO")
	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	
	@Column(name="NFS_CANCELAMENTO")
	public String getCancelamento() {
		return cancelamento;
	}
	public void setCancelamento(String cancelamento) {
		this.cancelamento = cancelamento;
	}
	
	@Column(name="NFS_DESCONTO")
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	
	
}