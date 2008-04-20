package br.ufc.TO;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ContasPagarTO implements Serializable {

	private String id;
	private String idDivisao;
	private String nomeDivisao;
	private String fornecedor;
	private String idFormasPagamento;
	private String idNotaFiscal;
	private String numeroNF;
	private String idPedidoDespesa;
	private String numeroPD;
	private String idParcela;
	private String numeroParcela;
	private String valorPrevista;
	private String dataPrevista;
	private String valorMonetario;
	private String valorEfetivo;
	private String dataEfetiva;
	private String observacao;
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

	public String getIdDivisao() {
		return idDivisao;
	}

	public void setIdDivisao(String idCliente) {
		this.idDivisao = idCliente;
	}

	public String getIdFormasPagamento() {
		return idFormasPagamento;
	}

	public void setIdFormasPagamento(String idFormasPagamento) {
		this.idFormasPagamento = idFormasPagamento;
	}

	public String getIdNotaFiscal() {
		return idNotaFiscal;
	}

	public void setIdNotaFiscal(String idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	public String getIdParcela() {
		return idParcela;
	}

	public void setIdParcela(String idParcela) {
		this.idParcela = idParcela;
	}

	public String getValorPrevista() {
		return valorPrevista;
	}

	public void setValorPrevista(String valorPrevista) {
		this.valorPrevista = valorPrevista;
	}

	public String getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(String dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public String getValorMonetario() {
		return valorMonetario;
	}

	public void setValorMonetario(String valorMonetario) {
		this.valorMonetario = valorMonetario;
	}

	public String getValorEfetivo() {
		return valorEfetivo;
	}

	public void setValorEfetivo(String valorEfetivo) {
		this.valorEfetivo = valorEfetivo;
	}

	public String getDataEfetiva() {
		return dataEfetiva;
	}

	public void setDataEfetiva(String dataEfetiva) {
		this.dataEfetiva = dataEfetiva;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
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

	public String getNumeroNF() {
		return numeroNF;
	}

	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}

	public String getNumeroPD() {
		return numeroPD;
	}

	public void setNumeroPD(String numeroPD) {
		this.numeroPD = numeroPD;
	}

}