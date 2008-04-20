package br.ufc.TO;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ContasReceberTO implements Serializable {

	private String id;
	private String idCliente;
	private String nomeCliente;
	private String idFormasPagamento;
	private String idNotaFiscal;
	private String numeroNotaFiscal;
	private String idParcela;
	private String numeroParcela;
	private String valorPrevista;
	private String dataPrevista;
	private String valorMonetario;
	private String valorEfetivo;
	private String dataEfetiva;
	private List<ObservacaoTO> observacao;
	private String diasAtraso;
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

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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

	public String getDiasAtraso() {
		return diasAtraso;
	}

	public void setDiasAtraso(String diasAtraso) {
		this.diasAtraso = diasAtraso;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public List<ObservacaoTO> getObservacao() {
		return observacao;
	}

	public void setObservacao(List<ObservacaoTO> observacao) {
		this.observacao = observacao;
	}
}