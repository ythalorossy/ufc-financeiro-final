package com.Auxiliar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`UNIDADES_PRODUCAO`")
public class Laboratorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997933725904752405L;

	private int id;
	private String nome;
	private String sigla;
	private String ativo;
	private int key;
	private String tipo;
	
	@Column(name="`UNP_SIGLA`")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	@Column(name="`UNP_ATIVO`")
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	@Column(name="`DIV_KEY`")
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	@Column(name="`UNP_TIPO`")
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Id
	@Column(name = "`UNP_KEY`")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="`UNP_NOME`")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
