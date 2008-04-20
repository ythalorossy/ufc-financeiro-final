package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ParcelaTO implements Serializable {

	private String id;
	private String numeroParcela;
	private String idNotaFiscal;
	private String numeroNF;
	private String idCliente;
	private String nomeCliente;
	private String dataPagamento;
	private String valor;
	private String status;
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

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(String numeroParcela) {
		this.numeroParcela = numeroParcela;
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
}