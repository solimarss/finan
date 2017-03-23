package br.com.solimar.finan.core.exception;


public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException() {
	}
	
	public BusinessException(String mensagem) {
		super(mensagem);
	}
	public BusinessException(Exception e) {
		super(e);
	}
	
}