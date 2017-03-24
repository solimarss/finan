package br.com.solimar.finan.view.categoria;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.CategoriaRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class CategoriaListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CategoriaRN categoriaRN;
	@Inject
	private UserSession userSession;
	
	private List<Categoria> categorias;
	
	@PostConstruct
	private void init(){
		search();
		
	}
	private void search() {
		categorias = categoriaRN.listAll(userSession.getContaApp());
	}
	
	
	protected void onSave(@Observes @On("categoria.save") Categoria evento) {
		search();
		UIService.update("categoria_list_form_id");

	}
	



	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	

}