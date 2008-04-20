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
@Table(name="OBSERVACAO")
public class Observacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2825623772845520756L;
	private int id;
	private String observacao;
	private GregorianCalendar data;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OB_ID")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="OB_OBSERVACAO")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Column(name="OB_DATA")
	@Type(type="calendar_date")
	public GregorianCalendar getData() {
		return data;
	}
	public void setData(GregorianCalendar data) {
		this.data = data;
	}
	
}
