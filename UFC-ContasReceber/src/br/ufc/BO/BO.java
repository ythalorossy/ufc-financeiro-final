package br.ufc.BO;

import java.util.List;

import br.ufc.DAO.DAO;

public interface BO<E> {

	public boolean save(E e);

	public boolean update(E e);

	public boolean delete(List<E> list);
	
	public boolean delete(E e);

	public List<E> findAll();

	public E findById(int id);

	public DAO<E> getDAO();
	
}