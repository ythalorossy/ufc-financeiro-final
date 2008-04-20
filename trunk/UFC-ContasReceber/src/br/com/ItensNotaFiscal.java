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
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="ITEMS_NOTA_FISCAL")
public class ItensNotaFiscal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6409775989359015842L;
	private int id;
	private NotaFiscal idNotaFiscal;
	private String numeroNF;
	private String idCliente;
	private String nomeDivisao;
	private String nomeLaboratorio;
	private int idDivisao;
	private int idLaboratorio;
	private String servico;
	private int quantidade;
	private double valor;
	private double valorTotal;
	private GregorianCalendar data;
	private int status;

	@Column(name="INF_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="INF_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="INF_NOTA_FISCAL")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_NOTA_FISCAL")
	public NotaFiscal getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(NotaFiscal idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	@Column(name="INF_ID_DIVISAO")
	public int getIdDivisao() {
		return idDivisao;
	}
	public void setIdDivisao(int idDivisao) {
		this.idDivisao = idDivisao;
	}
	@Column(name="INF_SERVICO")
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	@Column(name="INF_QUANTIDADE")
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	@Column(name="INF_VALOR")
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	@Column(name="INF_ID_CLIENTE")
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	@Column(name="INF_ID_VALOR_TOTAL_ITEM")
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	@Column(name="INF_NOME_DIVISAO")
	public String getNomeDivisao() {
		return nomeDivisao;
	}
	public void setNomeDivisao(String nomeDivisao) {
		this.nomeDivisao = nomeDivisao;
	}
	
	@Column(name="INF_ID_LABORATORIO")
	public int getIdLaboratorio() {
		return idLaboratorio;
	}
	public void setIdLaboratorio(int idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}
	@Column(name="INF_NOME_LAB")
	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}
	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}
	
	@Column(name="INF_NUMERO_NF")
	public String getNumeroNF() {
		return numeroNF;
	}
	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}
	
	@Column(name="INF_DATA")
	@Type(type="calendar_date")
	@OrderBy(clause="INF_DATA")
	public GregorianCalendar getData() {
		return data;
	}
	public void setData(GregorianCalendar data) {
		this.data = data;
	}
}