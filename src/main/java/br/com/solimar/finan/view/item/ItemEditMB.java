package br.com.solimar.finan.view.item;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.CategoriaRN;
import br.com.solimar.finan.business.ItemRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Item;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class ItemEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private ItemRN itemRN;
	
	@Inject
	private CategoriaRN categoriaRN;
	
	@Inject
	private UserSession userSession;

	@Inject
	@On("item.save")
	Event<Item> eventoItem;
	
	private Item item;
	private List<Categoria> categorias;
	
	

	@PostConstruct
	private void init() {
		item = new Item();
		categorias = categoriaRN.listAll(userSession.getContaApp());

	}

	public void abrirDialogNew() {
		this.item = new Item();
		UIService.update("item_edit_form_id");
		UIService.show("item_edit_wvar");
	}
	
	public void abrirDialogEdit(Item Item) {
		this.item = Item;
		UIService.update("item_edit_form_id");
		UIService.show("item_edit_wvar");
	}

	public String save() {
		
		try {

			item.setContaApp(userSession.getContaApp());
			itemRN.save(item);
			eventoItem.fire(item);
			
			UIService.hide("item_edit_wvar");
			UIService.showSuccess();

		} catch (Exception e) {
			UIService.showError(e);
		}
		return null;
	}

	
	public Item getItem() {
		return item;
	}

	public void setItem(Item Item) {
		this.item = Item;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

}