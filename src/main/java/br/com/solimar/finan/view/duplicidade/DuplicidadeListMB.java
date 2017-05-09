package br.com.solimar.finan.view.duplicidade;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class DuplicidadeListMB implements Serializable {

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
		lancamentos = lancamentoRN.findDuplicados(userSession.getContaApp(), userSession.getMes(),
				userSession.getAno());
	}

	public void excluir(Lancamento lancamento) {

		Lancamento lancamentoDuplicado = lancamentoRN.findDuplicados(lancamento, userSession.getMes(),
				userSession.getAno()).get(0);

		lancamentoDuplicado.setTransactionId(lancamento.getTransactionId());

		lancamentoRN.delete(lancamento);
		lancamentoRN.update(lancamentoDuplicado);
		search();
		UIService.update("duplicidade_list_form_id");

	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}