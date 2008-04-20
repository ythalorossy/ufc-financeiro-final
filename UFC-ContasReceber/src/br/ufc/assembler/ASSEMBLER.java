package br.ufc.assembler;

import java.util.List;

public interface ASSEMBLER<E,T> {
	
	public abstract E entityTO2Entity(T to);

	public abstract List<E> entityTO2Entity(List<T> to);

	public abstract T entity2EntityTO(E entity);

	public abstract List<T> entity2EntityTO(List<E> entity);

}