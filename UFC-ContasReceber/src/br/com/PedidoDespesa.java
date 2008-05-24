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
@Table(name = "PEDIDO_DESPESA")
public class PedidoDespesa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7735116402636173133L;
	private int id;
	private int idDivisao;
	private int idLaboratorio;
	private String tipoServico;
	private String numeroPD;
	private String justificativa;
	private String orcamento;
	private String fonteRecurso;
	private GregorianCalendar dataPD;
	private double valorPrevisto;
	private double valorCotado;
	private int status;
	private int projeto;
	private Integer anexos;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PD_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "PD_DIVISAO")
	public int getIdDivisao() {
		return idDivisao;
	}

	public void setIdDivisao(int idDivisao) {
		this.idDivisao = idDivisao;
	}

	@Column(name = "PD_TIPO_SERVICO")
	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	@Column(name = "PD_NUMERO_PD")
	public String getNumeroPD() {
		return numeroPD;
	}

	public void setNumeroPD(String numeroPD) {
		this.numeroPD = numeroPD;
	}

	@Column(name = "PD_JUSTIFICATIVA")
	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Column(name = "PD_ORCAMENTO")
	public String getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(String orcamento) {
		this.orcamento = orcamento;
	}

	@Column(name = "PD_FONTE_RECURSO")
	public String getFonteRecurso() {
		return fonteRecurso;
	}

	public void setFonteRecurso(String fonteRecurso) {
		this.fonteRecurso = fonteRecurso;
	}

	@Type(type = "calendar_date")
	@Column(name = "PD_DATA_PD")
	public GregorianCalendar getDataPD() {
		return dataPD;
	}

	public void setDataPD(GregorianCalendar dataPD) {
		this.dataPD = dataPD;
	}

	
	@Column(name = "PD_VALOR_PREVISTO")
	public double getValorPrevisto() {
		return valorPrevisto;
	}

	public void setValorPrevisto(double valorPrevisto) {
		this.valorPrevisto = valorPrevisto;
	}

	@Column(name = "PD_VALOR_COTADO")
	public double getValorCotado() {
		return valorCotado;
	}

	public void setValorCotado(double valorCotado) {
		this.valorCotado = valorCotado;
	}

	@Column(name = "PD_STATUS")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="PD_ID_LAB")
	public int getIdLaboratorio() {
		return idLaboratorio;
	}

	public void setIdLaboratorio(int idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	@Column(name="PD_PROJETO")
	public int getProjeto() {
		return projeto;
	}

	public void setProjeto(int projeto) {
		this.projeto = projeto;
	}
	
	@Column(name="PD_ANEXO")
	public Integer getAnexos() {
		return anexos;
	}

	public void setAnexos(Integer anexos) {
		this.anexos = anexos;
	}


}
