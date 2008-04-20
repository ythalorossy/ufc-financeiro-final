package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotaFiscalTO implements Serializable {

	private String id;
	private String idCliente;
	private String nomeCliente;
	private String numeroProcesso;
	private String numeroContrato;
	private String notaFiscal;
	private String dataSaida;
	private String valorNota;
	private String tipoNota; // nota contabilizada ou não contabilizada
	private String status; // Nota ativa ou cancelada
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

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(String notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getValorNota() {
		return valorNota;
	}

	public void setValorNota(String valorNota) {
		this.valorNota = valorNota;
	}

	public String getTipoNota() {
		return tipoNota;
	}

	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

}