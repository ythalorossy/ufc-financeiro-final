package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItensPedidoDespesaTO implements Serializable{
	
	private String id;
	private String idPedidoDespesa;
	private String nomeDivisao;
	private String idDivisao;
	private String item;
	private String unidade;
	private String quantidade;
	private String valorUnitario;
	private String valorTotal;
	private String valorUnitarioCotado;
	private String valorTotalCotado;
	private String status;
	private boolean checked;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPedidoDespesa() {
		return idPedidoDespesa;
	}
	public void setIdPedidoDespesa(String idPedidoDespesa) {
		this.idPedidoDespesa = idPedidoDespesa;
	}
	public String getNomeDivisao() {
		return nomeDivisao;
	}
	public void setNomeDivisao(String nomeDivisao) {
		this.nomeDivisao = nomeDivisao;
	}
	public String getIdDivisao() {
		return idDivisao;
	}
	public void setIdDivisao(String idDivisao) {
		this.idDivisao = idDivisao;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getValorUnitarioCotado() {
		return valorUnitarioCotado;
	}
	public void setValorUnitarioCotado(String valorUnitarioCotado) {
		this.valorUnitarioCotado = valorUnitarioCotado;
	}
	public String getValorTotalCotado() {
		return valorTotalCotado;
	}
	public void setValorTotalCotado(String valorTotalCotado) {
		this.valorTotalCotado = valorTotalCotado;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	

}
