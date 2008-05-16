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
@Table(name="ACOMPANHAMENTO_PD")
public class AcompanhamentoPD implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6916530393516694891L;
	
	private int id;
	private PedidoDespesa pd;
	private GregorianCalendar dataEnvio;
	private GregorianCalendar dataRecebimento;
	private int laboratorio;
	private int divisao;
	private int status;
	private String observacao;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="APD_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="APD_ID_PD")
	@ForeignKey(name="ID_PD_FK")
	@LazyToOne(value=LazyToOneOption.FALSE)
	public PedidoDespesa getPd() {
		return pd;
	}
	public void setPd(PedidoDespesa pd) {
		this.pd = pd;
	}
	
	@Column(name="APD_DATA_ENVIO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(GregorianCalendar dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	
	@Column(name="APD_DATA_RECEBIMENTO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(GregorianCalendar dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	
	@Column(name="APD_ID_UPRODUCAO")
	public int getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(int divisao) {
		this.laboratorio = divisao;
	}
	
	@Column(name="APD_OBSERVACAO")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name="APD_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="APD_ID_DIVISAO")
	public int getDivisao() {
		return divisao;
	}
	public void setDivisao(int divisao) {
		this.divisao = divisao;
	}
}
