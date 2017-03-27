package br.com.solimar.finan.view.conta;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.ContaRN;
import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class ContaEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ContaRN contaRN;
	@Inject
	private UserSession userSession;

	@Inject
	@On("conta.save")
	Event<Conta> eventoConta;
	
	private Conta conta;

	@PostConstruct
	private void init() {
		conta = new Conta();

	}

	public void abrirDialogNew() {
		this.conta = new Conta();
		conta.setLancamentoManual(true);
		UIService.update("conta_edit_form_id");
		UIService.show("conta_edit_wvar");
	}
	
	public void abrirDialogEdit(Conta conta) {
		this.conta = conta;
		UIService.update("conta_edit_form_id");
		UIService.show("conta_edit_wvar");
	}

	public String save() {
		
		try {

			conta.setContaApp(userSession.getContaApp());
			contaRN.save(conta);
			eventoConta.fire(conta);
			
			UIService.hide("conta_edit_wvar");
			UIService.showSuccess();

		} catch (Exception e) {
			UIService.showError(e);
		}
		return null;
	}

	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}