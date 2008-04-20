package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CaixaTO implements Serializable {

	private String id;
	private String idContasReceber;
	private String idContasPagar;
	private String idCaixaEntradaSaida;
	private String doc;
	private String descricaoServico;
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

	public String getIdContasReceber() {
		return idContasReceber;
	}

	public void setIdContasReceber(String idContasReceber) {
		this.idContasReceber = idContasReceber;
	}

	public String getIdContasPagar() {
		return idContasPagar;
	}

	public void setIdContasPagar(String idContasPagar) {
		this.idContasPagar = idContasPagar;
	}

	public String getIdCaixaEntradaSaida() {
		return idCaixaEntradaSaida;
	}

	public void setIdCaixaEntradaSaida(String idCaixaEntradaSaida) {
		this.idCaixaEntradaSaida = idCaixaEntradaSaida;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getDescricaoServico() {
		return descricaoServico;
	}

	public void setDescricaoServico(String descricaoServico) {
		this.descricaoServico = descricaoServico;
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

}