package br.com.solimar.finan.view.categoria;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
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
public class CategoriaEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private CategoriaRN categoriaRN;
	@Inject
	private UserSession userSession;

	@Inject
	@On("categoria.save")
	Event<Categoria> eventoCategoria;
	
	private Categoria categoria;

	@PostConstruct
	private void init() {
		categoria = new Categoria();

	}

	public void abrirDialogNew() {
		this.categoria = new Categoria();
		UIService.update("categoria_edit_form_id");
		UIService.show("categoria_edit_wvar");
	}
	
	public void abrirDialogEdit(Categoria categoria) {
		this.categoria = categoria;
		UIService.update("categoria_edit_form_id");
		UIService.show("categoria_edit_wvar");
	}

	public String save() {
		
		try {

			categoria.setContaApp(userSession.getContaApp());
			categoriaRN.save(categoria);
			eventoCategoria.fire(categoria);
			
			UIService.hide("categoria_edit_wvar");
			UIService.showSuccess();

		} catch (Exception e) {
			UIService.showError(e);
		}
		return null;
	}

	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}