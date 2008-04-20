package br.com;

import java.io.Serializable;

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

@Entity
@Table(name="ITENS_PEDIDO_DESPESA")
public class ItensPedidoDespesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 174940955302489867L;
	private int id;
	private PedidoDespesa idPedidoDespesa;
	private String nomeDivisao;
	private int idDivisao;
	private String item;
	private String unidade;
	private int quantidade;
	private double valorUnitario;
	private double valorTotal;
	private double valorUnitarioCotado;
	private double valorTotalCotado;
	private int status;
	
	public ItensPedidoDespesa(double valorTotal, double valorTotalCotado) {
		this.valorTotal = valorTotal;
		this.valorTotalCotado = valorTotalCotado;
	}
	

	public ItensPedidoDespesa() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IPD_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="IPD_ID_PEDIDO_DESPESA")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_ID_PEDIDO_DESPESA")
	public PedidoDespesa getIdPedidoDespesa() {
		return idPedidoDespesa;
	}
	public void setIdPedidoDespesa(PedidoDespesa idPedidoDespesa) {
		this.idPedidoDespesa = idPedidoDespesa;
	}
	
	@Column(name="IPD_NOME_DIVISAO")
	public String getNomeDivisao() {
		return nomeDivisao;
	}
	public void setNomeDivisao(String nomeDivisao) {
		this.nomeDivisao = nomeDivisao;
	}
	
	@Column(name="IPD_DIVISAO")
	public int getIdDivisao() {
		return idDivisao;
	}
	public void setIdDivisao(int idDivisao) {
		this.idDivisao = idDivisao;
	}
	
	@Column(name="IPD_ITEM")
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	@Column(name="IPD_UNIDADE")
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@Column(name="IPD_QUANTIDADE")
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name="IPD_VALOR_UNITARIO")
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	@Column(name="IPD_VALOR_TOTAL")
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	@Column(name="IPD_VALOR_UNITARIO_COTADO")
	public double getValorUnitarioCotado() {
		return valorUnitarioCotado;
	}
	public void setValorUnitarioCotado(double valorUnitarioCotado) {
		this.valorUnitarioCotado = valorUnitarioCotado;
	}
	@Column(name="IPD_VALOR_TOTAL_COTADO")
	public double getValorTotalCotado() {
		return valorTotalCotado;
	}
	public void setValorTotalCotado(double valorTotalCotado) {
		this.valorTotalCotado = valorTotalCotado;
	}
	@Column(name="IPD_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
