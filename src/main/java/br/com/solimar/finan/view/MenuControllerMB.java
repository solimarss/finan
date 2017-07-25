package br.com.solimar.finan.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.enums.LancTipoListagemEnum;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.view.application.UserSession;

@Named
@RequestScoped
public class MenuControllerMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserSession userSession;

	public String getItemCssClass(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();

		// viewId = "/pages/" + viewId + ".xhtml";
		viewId = "/pages/" + viewId;

		// log.info("[getItemCssClass] currentViewId: "+currentViewId);
		// System.out.println("viewId "+viewId);
		// System.out.println("currentViewId "+currentViewId);

		return currentViewId.contains(viewId) ? "is-selected" : null;
	}
	
	public String goToLancamentos() {
		System.out.println("goToLancamentos()");
		userSession.setListagem(LancTipoListagemEnum.T);
		return "/pages/lancamento/lancamento_list.jsf?faces-redirect=true";
	}

	public String goToDespesas() {
		System.out.println("goToDespesas");
		userSession.setListagem(LancTipoListagemEnum.S);
		
		return "/pages/lancamento/lancamento_list.jsf?faces-redirect=true";
	}
	
	public String goToTiposDespesas() {
		System.out.println("goToDespesas");
		userSession.setListagem(LancTipoListagemEnum.S);
		return "/pages/tipo/tipo_list.jsf?faces-redirect=true";
	}
	
	public String goToCategoriasDespesas() {
		System.out.println("goToDespesas");
		userSession.setListagem(LancTipoListagemEnum.S);
		return "/pages/categoria/categoria_list.jsf?faces-redirect=true";
	}
	public String goToReceitas() {
		System.out.println("goToReceitas");
		userSession.setListagem(LancTipoListagemEnum.E);
		return "/pages/lancamento/lancamento_list.jsf?faces-redirect=true";
	}

}
