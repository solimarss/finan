package br.com.solimar.finan.core.exception;


public class BusinessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessRuntimeException() {
	}
	
	public BusinessRuntimeException(String mensagem) {
		super(mensagem);
	}
	
	public BusinessRuntimeException(Exception e) {
		super(e);
	}
	
}