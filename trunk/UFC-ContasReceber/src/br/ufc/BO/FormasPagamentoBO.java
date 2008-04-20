package br.ufc.BO;

import java.util.List;

import br.com.FormasPagamento;
import br.ufc.DAO.DAO;
import br.ufc.DAO.FormasPagamentoDAO;

public class FormasPagamentoBO implements BO<FormasPagamento> {

	DAO<FormasPagamento> dao = new FormasPagamentoDAO();

	public boolean delete(FormasPagamento e) {
		return dao.delete(e);
	}

	public boolean delete(List<FormasPagamento> e) {
		return ((FormasPagamentoDAO)dao).delete(e);
	}

	public boolean save(FormasPagamento e) {
		return dao.save(e);
	}

	public boolean update(FormasPagamento e) {
		return dao.update(e);
	}

	public List<FormasPagamento> findAll() {
		return dao.findAll();
	}

	public FormasPagamento findById(int id) {
		return dao.findById(id);
	}

	public DAO<FormasPagamento> getDAO() {
		return dao;
	}

	public static BO<FormasPagamento> getInstance() {
		return new FormasPagamentoBO();
	}

	public FormasPagamento findByFormaPagamento(String idFormaPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

}
