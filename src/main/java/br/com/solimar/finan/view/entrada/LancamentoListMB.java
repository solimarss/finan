package br.com.solimar.finan.view.entrada;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.view.JSFUtil;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class LancamentoListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;
	@Inject
	private UserSession userSession;

	private List<Lancamento> lancamentos;

	private String tipoLancamentoView;

	@PostConstruct
	private void init() {

		tipoLancamentoView = JSFUtil.getUrlParameter("tipo");
		System.out.println("tipoLancamentoView: "+tipoLancamentoView);

		search();
	}

	public void search() {
		if(tipoLancamentoView.equals("receita")){
			lancamentos = lancamentoRN.findEntradas(userSession.getContaApp());
		}else{
			lancamentos = lancamentoRN.findSaidas(userSession.getContaApp());
		}
		
	}

	protected void onSave(@Observes @On("entrada.save") Lancamento evento) {
		search();
		UIService.update("lancamento_list_form_id");
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}