package br.ufc.exception;

public class ListSizeException extends Exception {

	private static final long serialVersionUID = -9068339243183909231L;

	public ListSizeException() {
		super("Tamanho da lista maior que o permitido");
	}
	
	public ListSizeException(String erro) {
		super(erro);
	}
	
}
