package br.com;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FORMAS_PAGAMENTO")
public class FormasPagamento implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 727718292648020010L;
	private int id;
	private String formaPagamento;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FPG_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="FPG_FORMA_PAGAMENTO")
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	

}
