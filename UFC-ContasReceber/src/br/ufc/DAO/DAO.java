package br.ufc.DAO;

import java.util.List;

public interface DAO<E> {

	public abstract E findById(int id);

	public abstract List<E> findAll();
	
	public abstract boolean save(E e);
	
	public abstract boolean update(E e);
	
	public abstract boolean delete(E e);

	public abstract E findByObject(E e);

}