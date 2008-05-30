package br.ufc.uteis;

import com.Auxiliar.Clientes;
import com.hibernate.HibernateHelperCliente;

public class PopulaBanco {

	private String nome;

	private String id;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static void main(String[] args) {
		new PopulaBanco().gerar();
	}

	private void gerar() {

		HibernateHelperCliente hc = HibernateHelperCliente.getInstance();

		String[] alfa = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
				"i", "j", "l", "m", "n", "o", "p", "q", "r" };

		hc.getSession().beginTransaction();

		for (int i = 0; i < 100000; i++) {
			Clientes c = new Clientes();
			c.setAreaAtividade("");
			c.setCelular("");
			c.setCep("");
			c.setCidade("");
			c.setContato("");
			c.setCxPostal("");
			c.setEmail("");
			c.setEndereco("");
			c.setFax("");
			c.setFone("");
			c.setInsest("");
			c.setTipo("");
			c.setUf("");

			setNome("");
			setId("");

			while (getNome().length() < 11) {

				int index = 0;

				do {
					index = (int) (Math.random() * (alfa.length));
				} while (index == 0);

				setId(getId() + String.valueOf(index));
				setNome(getNome() + alfa[index]);
			}

			setNome(getNome() + " ");

			while (getNome().length() < 23) {
				setNome(getNome() + alfa[(int) (Math.random() * (alfa.length))]);
			}

			c.setId(id);
			c.setNome(nome);

			setId(null);
			setNome(null);

			hc.save(c);

		}
		hc.getSession().getTransaction().commit();
		hc.close();

	}

}
