package br.com.solimar.finan.core.exception;

import javax.ejb.ApplicationException;

/*
 * Com anotação @ApplicationException(rollback=true) podemos determinar se a execeção causará ou não rollback
 * Se a exeção estender de 'Exception' ela será 'checked', ou seja, obrigará tratamento de exeção
 * Se a exeção estender de 'BusinessRuntimeException' ela será unchecked, ou seja, não obrigará tratamento de exeção
 * 
 */

@ApplicationException(rollback = true)
public class ExampleException extends Exception {
	private static final long serialVersionUID = 1L;

}