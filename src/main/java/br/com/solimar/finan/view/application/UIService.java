package br.com.solimar.finan.view.application;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.solimar.finan.util.UtilErros;



public class UIService {
	
	
	private static RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
	
	public static void hide(String wvar) {
		wvar = String.format("PF('%s').hide()", wvar);
		//log.debug("[hide] wvar : {}", wvar);
		getRequestContext().execute(wvar);
	}
	
	public static void update(String id) {
		//log.debug("[update] id: {}", id);
		System.out.println("[update] id: "+id);
		getRequestContext().update(id);
	}
	public static void showGrowl() {
		UIService.update("growl_id");
	}
	
	public static void showError() {
		UIService.update("error_form_id:error_body_id");
		UIService.show("error_wvar");
	}
	
	public static void showError(Exception e) {
		e.printStackTrace();
		showError(e.toString());
	}
	
	public static void showError(String msg, Exception e) {
		e.printStackTrace();
		showError(msg);
	}
	
	private static void showError(String msg) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		//message.setSummary("Erro: " + UtilErros.getMensagemErro(e));
		message.setSummary("Erro: " + msg);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,message);
		UIService.update("error_form_id:error_body_id");
		UIService.show("error_wvar");
	}
	
	public static  void show(String wvar) {
		wvar = String.format("PF('%s').show()", wvar);
		//log.debug("[show] wvar : {}", wvar);
		getRequestContext().execute(wvar);
	}

	
}
