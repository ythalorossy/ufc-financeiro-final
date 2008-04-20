package br.com;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="CAIXA_ENTRADA_SAIDA")
public class CaixaEntradaSaida implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4094831951125117496L;
	private int id;
	private int operacao;
	private GregorianCalendar dataTransacao;
	private String descricao;
	private double valor;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CES_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="CES_OPERACAO")
	public int getOperacao() {
		return operacao;
	}
	public void setOperacao(int operacao) {
		this.operacao = operacao;
	}
	@Column(name="DATA_TRANSACAO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(GregorianCalendar dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	@Column(name="CES_DESCRICAO")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Column(name="CES_VALOR")
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}