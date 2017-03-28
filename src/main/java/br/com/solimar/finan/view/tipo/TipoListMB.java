package br.com.solimar.finan.view.tipo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.TipoRN;
import br.com.solimar.finan.entity.Tipo;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class TipoListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private TipoRN itemRN;
	@Inject
	private UserSession userSession;
	
	private List<Tipo> items;
	
	
	@PostConstruct
	private void init(){
		search();
		
	}
	private void search() {
		items = itemRN.listAll(userSession.getContaApp());
	}
	
	
	protected void onSave(@Observes @On("item.save") Tipo evento) {
		search();
		UIService.update("item_list_form_id");
		
	}
	



	public List<Tipo> getItems() {
		return items;
	}

	public void setItems(List<Tipo> items) {
		this.items = items;
	}

	

}