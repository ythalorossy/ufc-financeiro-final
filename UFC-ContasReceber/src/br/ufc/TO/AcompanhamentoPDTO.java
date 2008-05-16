package br.ufc.TO;

import java.io.Serializable;

public class AcompanhamentoPDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1383732073383955399L;
	
	private String id;
	private String pd;
	private String numeroPD;
	private String dataEnvio;
	private String dataRecebimento;
	private String laboratorio;
	private String nomeLaboratorio;
	private String divisao;
	private String nomeDivisao;
	private String observacao;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public String getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String divisao) {
		this.laboratorio = divisao;
	}
	public String getNomeLaboratorio() {
		return nomeLaboratorio;
	}
	public void setNomeLaboratorio(String nomeDivisao) {
		this.nomeLaboratorio = nomeDivisao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getNumeroPD() {
		return numeroPD;
	}
	public void setNumeroPD(String numeroPD) {
		this.numeroPD = numeroPD;
	}
	public String getNomeDivisao() {
		return nomeDivisao;
	}
	public void setNomeDivisao(String nomeDivisao) {
		this.nomeDivisao = nomeDivisao;
	}
	public String getDivisao() {
		return divisao;
	}
	public void setDivisao(String divisao) {
		this.divisao = divisao;
	}
	
	

}
