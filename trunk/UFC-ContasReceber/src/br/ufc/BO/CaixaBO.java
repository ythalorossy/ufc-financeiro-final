package br.ufc.BO;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.Caixa;
import br.com.CaixaEntradaSaida;
import br.com.ContasPagar;
import br.com.ContasReceber;
import br.ufc.DAO.CaixaDAO;
import br.ufc.DAO.DAO;
import br.ufc.uteis.Status;

public class CaixaBO implements BO<Caixa> {

	private DAO<Caixa> dao = new CaixaDAO();
	private boolean result = false;

	public boolean delete(Caixa e) {

		/*
		 * Verifica se o Caixa nao esta aberto
		 */
		if (e.getStatus() != Status.ABERTO) {

			result = dao.delete(e);
		}
		return result;

	}

	public List<Caixa> findAll() {
		return dao.findAll();
	}

	/**
	 * Retorna um caixa por ID
	 */
	public Caixa findById(int id) {
		return dao.findById(id);
	}

	public DAO<Caixa> getDAO() {
		return dao;
	}

	public static BO<Caixa> getInstance() {
		return new CaixaBO();
	}

	/**
	 * Bater Caixa: Atualiza o status de Batido de todas os caixas de uma 
	 * determinada data
	 * @param calendar
	 * @return
	 */
	public boolean baterCaixa(GregorianCalendar calendar) {
		final List<Caixa> listCaixa = findAllByDia(calendar);
		for (Caixa c : listCaixa) {
			c.setStatus(Status.BATIDO);
		}
		return ((CaixaDAO) dao).baterCaixa(listCaixa);
	}

	/**
	 * Reabrir caixa: Atualiza o status de Aguardando para todos os caixas de uma derterminada data
	 * @param calendar
	 * @return
	 */
	public boolean reabrirCaixa(GregorianCalendar calendar) {
		/*
		 * Todos os caixas de uma determinada data
		 */
		final List<Caixa> caixaList = findAllByDia(calendar);
		return ((CaixaDAO) dao).reabrirCaixa(caixaList);
	}

	/**
	 * Retorna todos os caixas de uma determinada data
	 * @param calendar
	 * @return
	 */
	public List<Caixa> findAllByDia(GregorianCalendar calendar) {

		return ((CaixaDAO) dao).findAllByDia(calendar);
	}

	/**
	 * Retorna todos os caixas anteriores a uma determinada data passada
	 * @param calendar
	 * @return
	 */
	public List<Caixa> findAllAnterior(GregorianCalendar calendar) {

		return ((CaixaDAO) dao).findAllAnterior(calendar);
	}

	public boolean delete(List<Caixa> list) {
		return false;
	}

	/**
	 * Extornar um Caixa e atuliza as suas dependencias
	 * @param caixa
	 * @return
	 */
	public boolean extornarCaixa(Caixa caixa) {

		/**
		 * Verifica se o Caixa é do tipo Caixa Entrada e Saida
		 */
		if (caixa.getIdCaixaEntradaSaida() != null) {
			
			/*
			 * Monta caixa entrada e saida baseado nas informacoes do caixa
			 */
			CaixaEntradaSaida ces = CaixaEntradaSaidaBO.getInstance().findById(caixa.getIdCaixaEntradaSaida().getId());
			
			if (((CaixaDAO)dao).extornarCaixa(caixa, ces) ) {
				result = true;
			}
			
		}

		/**
		 * Verifica se o caixa é um Contas a Pagar
		 */
		if (caixa.getIdContasPagar() != null) {

			/*
			 * Monta Contas a Pagar baseado nas informações do caixa
			 */
			
			ContasPagar cp = ContasPagarBO.getInstance().findById(caixa.getIdContasPagar().getId());
					
			if (((ContasPagarBO)ContasPagarBO.getInstance()).reabrirConta(cp)) {
				result = true;
			}
		}
		
		/**
		 * Verifica se o caixa é um Contas a Receber
		 */
		if (caixa.getIdContasReceber() != null) {

			/*
			 * Monta Contas a receber baseado nas informacoes do caixa
			 */
			ContasReceber contasReceber = ContasReceberBO.getInstance().findById(caixa.getIdContasReceber().getId());
			if (((ContasReceberBO)ContasReceberBO.getInstance()).reabrirConta(contasReceber)){
				result = true;
			}
			
		}
		
		return result;
	}

	public boolean save(Caixa e) {
		return false;
	}

	public boolean update(Caixa e) {
		return false;
	}

	public Caixa findByIdContaPagar(int id) {
		return ((CaixaDAO)dao).findByIdContaPagar(id);
	}

	public Caixa findByIdContaReceber(int id) {
		return ((CaixaDAO)dao).findByIdContaReceber(id);
	}

	
}
