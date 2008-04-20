package br.ufc.TO;

import java.io.Serializable;

/**
 * @author Administrador
 * 
 */
@SuppressWarnings("serial")
public class PedidoDespesaTO implements Serializable{

	private String id;
	private String idDivisao;
	private String idLaboratorio;
	private String nomeLaboratorio;
	private String nomeDivisao;
	private String tipoServico;
	private String numeroPD;
	private String justificativa;
	private String orcamento;
	private String fonteRecurso;
	private String dataPD;
	private String valorPrevisto;
	private String valorCotado;
	private String status;
	private boolean checked;
	private String projeto;
	

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

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

	public String getIdDivisao() {
		return idDivisao;
	}

	public void setIdDivisao(String idDivisao) {
		this.idDivisao = idDivisao;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getNumeroPD() {
		return numeroPD;
	}

	public void setNumeroPD(String numeroPD) {
		this.numeroPD = numeroPD;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(String orcamento) {
		this.orcamento = orcamento;
	}

	public String getFonteRecurso() {
		return fonteRecurso;
	}

	public void setFonteRecurso(String fonteRecurso) {
		this.fonteRecurso = fonteRecurso;
	}

	public String getDataPD() {
		return dataPD;
	}

	public void setDataPD(String dataPD) {
		this.dataPD = dataPD;
	}

	public String getValorPrevisto() {
		return valorPrevisto;
	}

	public void setValorPrevisto(String valorPrevisto) {
		this.valorPrevisto = valorPrevisto;
	}

	public String getValorCotado() {
		return valorCotado;
	}

	public void setValorCotado(String valorCotado) {
		this.valorCotado = valorCotado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
