package br.ufc.assembler;

import br.ufc.DAO.ClientesDAO;

import com.Auxiliar.Clientes;

public class ClienteAssembler {
	
	public String retornaCliente(String idCliente){
		Clientes cliente = new ClientesDAO().findById(idCliente);
		if(cliente == null){
			cliente = new Clientes();
			cliente.setNome(idCliente+" : Apagado do Banco");
		}
		return cliente.getNome();
	}

}
