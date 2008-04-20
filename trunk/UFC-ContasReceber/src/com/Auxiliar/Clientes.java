package com.Auxiliar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Clientes")
public class Clientes {

	private String id;
	private String nome;
	private String endereco;
	private String cep;
	private String cidade;
	private String uf;
	private String contato;
	private String tipo;
	private String areaAtividade;
	private String fone;
	private String fax;
	private String cxPostal;
	private String email;
	private String insest;
	private String celular;

	@Id
	@Column(name = "CLI_CGCPF")
	public String getId() {
		return id;
	}

	public void setId(String cgcpf) {
		this.id = cgcpf;
	}

	@Column(name = "CLI_NOME")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CLI_END")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "CLI_CEP")
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "CLI_CITY")
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "CLI_UF")
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Column(name = "CLI_CONTAT")
	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	@Column(name = "CLI_TIPO")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "CLI_AREATIV")
	public String getAreaAtividade() {
		return areaAtividade;
	}

	public void setAreaAtividade(String areaAtividade) {
		this.areaAtividade = areaAtividade;
	}

	@Column(name = "CLI_FONE")
	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	@Column(name = "CLI_FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "CLI_CXPOST")
	public String getCxPostal() {
		return cxPostal;
	}

	public void setCxPostal(String cxPostal) {
		this.cxPostal = cxPostal;
	}

	@Column(name = "CLI_EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CLI_INSEST")
	public String getInsest() {
		return insest;
	}

	public void setInsest(String insest) {
		this.insest = insest;
	}

	@Column(name = "CLI_CELULAR")
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}
