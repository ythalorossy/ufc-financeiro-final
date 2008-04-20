package br.ufc.uteis;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.Caixa;
import br.ufc.BO.CaixaBO;

public class CaixaUteis {

	public static CaixaUteis getInstance() {
		return new CaixaUteis();
	}
	
	public List<Caixa> findAllByDia(GregorianCalendar calendar){
		
		return ((CaixaBO)CaixaBO.getInstance()).findAllByDia(calendar);
	}
	
	public void reabrirContaReceber (int idContaReceber){
		
		/*
		 * Na tabela contas a receber buscar o a conta pelo Id,
		 * E muda status para Aberto
		 * 
		 * pegar o id da Parcela do Objeto contas as receber, ir na
		 * tabela Parcela e alterar o status para aberto
		 * 
		 */
		
	}
	
}
