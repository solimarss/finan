package br.com.solimar.finan.view.application;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.primefaces.context.RequestContext;

public class ValidationListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Em caso de falha de validação exibe o dialog de erro do sistema
	 */
	@Override
	public void afterPhase(PhaseEvent event) {
		if (event.getFacesContext().isValidationFailed()) {
			
			RequestContext.getCurrentInstance().update("error_form_id:error_body_id");
			RequestContext.getCurrentInstance().execute("PF('error_wvar').show()");
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.PROCESS_VALIDATIONS;
	}

}
