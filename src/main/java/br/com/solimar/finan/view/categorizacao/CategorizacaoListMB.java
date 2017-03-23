package br.com.solimar.finan.view.categorizacao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class CategorizacaoListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;
	@Inject
	private UserSession userSession;
	
	private List<Lancamento> lancamentos;
	
	@PostConstruct
	private void init(){
		lancamentos = lancamentoRN.findNaoCategorizados(userSession.getContaApp());
		
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	

	

}