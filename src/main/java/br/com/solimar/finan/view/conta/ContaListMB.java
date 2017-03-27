package br.com.solimar.finan.view.conta;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
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
public class ContaListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ContaRN contaRN;
	@Inject
	private UserSession userSession;
	
	private List<Conta> contas;
	
	@PostConstruct
	private void init(){
		search();
		
	}
	private void search() {
		contas = contaRN.listAll(userSession.getContaApp());
	}
	
	
	protected void onSave(@Observes @On("conta.save") Conta evento) {
		search();
		UIService.update("conta_list_form_id");

	}
	



	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	

}