package com.Auxiliar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DIVISOES")
public class Divisao {
	
	private int id;
	private String nome;
	private String sigla;
	private int ativo;
	private int dirKey;
	
	@Column(name="DIV_SIGLA")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name="DIV_ATIVO")
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	@Column(name="DIR_KEY")
	public int getDirKey() {
		return dirKey;
	}
	public void setDirKey(int dirKey) {
		this.dirKey = dirKey;
	}
	@Id
	@Column(name="DIV_KEY")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="DIV_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
