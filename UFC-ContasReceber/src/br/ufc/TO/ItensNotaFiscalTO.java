package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItensNotaFiscalTO implements Serializable{
	
	private String id;
	private String idNotaFiscal;
	private String numeroNF;
	private String idCliente;
	private String nomeCliente;
	private String idDivisao;
	private String nomeDivisao;
	private String idLaboratorio;
	private String nomeLaboratorio;
	private String servico;
	private String quantidade;
	private String valor;
	private String valorTotal;
	private String status;
	private String data;
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(String idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getIdDivisao() {
		return idDivisao;
	}
	public void setIdDivisao(String idDivisao) {
		this.idDivisao = idDivisao;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumeroNF() {
		return numeroNF;
	}
	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getNomeDivisao() {
		return nomeDivisao;
	}
	public void setNomeDivisao(String nomeDivisao) {
		this.nomeDivisao = nomeDivisao;
	}
	public String getIdLaboratorio() {
		return idLaboratorio;
	}
	public void setIdLaboratorio(String idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}
	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}
	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	

}