package br.com;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;

@Entity
@Table(name="CONTAS_RECEBER")
public class ContasReceber implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7108574080089624865L;
	private int id;
	private String idCliente;
	private FormasPagamento idFormasPagamento;
	private NotaFiscal idNotaFiscal;
	private int idParcela;
	private double valorPrevista;
	private GregorianCalendar dataPrevista;
	private double valorMonetario;
	private double valorEfetivo;
	private GregorianCalendar dataEfetiva;
	private List<Observacao> observacao;
	private int status;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CTR_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="CTR_ID_CLIENTE")
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	@ManyToOne
	@JoinColumn(name="CTR_ID_FORMA_PAGAMENTO")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_FORMA_PAGAMENTO")
	public FormasPagamento getIdFormasPagamento() {
		return idFormasPagamento;
	}
	public void setIdFormasPagamento(FormasPagamento idFormasPagamento) {
		this.idFormasPagamento = idFormasPagamento;
	}
	@ManyToOne
	@JoinColumn(name="CTR_ID_NOTA_FISCAL")
	@LazyToOne(value=LazyToOneOption.FALSE)
	@ForeignKey(name="FK_NOTA_FISCAL")
	public NotaFiscal getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(NotaFiscal idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	@Column(name="CTR_PARCELA")
	public int getIdParcela() {
		return idParcela;
	}
	public void setIdParcela(int idParcela) {
		this.idParcela = idParcela;
	}
	@Column(name="CTR_VALOR_PREVISTO")
	public double getValorPrevista() {
		return valorPrevista;
	}
	public void setValorPrevista(double valorPrevista) {
		this.valorPrevista = valorPrevista;
	}
	@Column(name="CTR_DATA_PREVISTA")
	@Type(type="calendar_date")
	public GregorianCalendar getDataPrevista() {
		return dataPrevista;
	}
	public void setDataPrevista(GregorianCalendar dataPrevista) {
		this.dataPrevista = dataPrevista;
	}
	@Column(name="CTR_VALOR_MONETARIO")
	public double getValorMonetario() {
		return valorMonetario;
	}
	public void setValorMonetario(double valorMonetario) {
		this.valorMonetario = valorMonetario;
	}
	@Column(name="CTR_VALOR_EFETIVO")
	public double getValorEfetivo() {
		return valorEfetivo;
	}
	public void setValorEfetivo(double valorEfetivo) {
		this.valorEfetivo = valorEfetivo;
	}
	@Column(name="CTR_DATA_EFETIVO")
	@Type(type="calendar_date")
	public GregorianCalendar getDataEfetiva() {
		return dataEfetiva;
	}
	public void setDataEfetiva(GregorianCalendar dataEfetiva) {
		this.dataEfetiva = dataEfetiva;
	}
	@Column(name="CTR_STATUS")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="CTR_OBSERVACAO")
	@LazyCollection(value=LazyCollectionOption.FALSE)
	public List<Observacao> getObservacao() {
		return observacao;
	}
	
	public void setObservacao(List<Observacao> observacao) {
		this.observacao = observacao;
	}
}