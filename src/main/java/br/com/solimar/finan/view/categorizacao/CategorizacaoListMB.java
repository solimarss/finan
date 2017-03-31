package br.com.solimar.finan.view.categorizacao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
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
	private void init() {
		search();
	}

	public void search() {
		lancamentos = lancamentoRN.findNaoCategorizados(userSession.getContaApp(), userSession.getMes(), userSession.getAno());
	}

	protected void onSave(@Observes @On("categorizacao.save") Lancamento evento) {
		search();
		UIService.update("categorizacao_list_form_id");
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	

}