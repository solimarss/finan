package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Item;
import br.com.solimar.finan.persistence.ItemDAO;

@Stateless
public class ItemRN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ItemDAO ItemDAO;

	public void insert(Item entity) {
		ItemDAO.insert(entity);
	}

	public void update(Item entity) {
		ItemDAO.update(entity);
	}

	public void save(Item entity) {
		if (entity.getId() == null) {
			System.out.println("INSERT");
			ItemDAO.insert(entity);
		} else {
			ItemDAO.update(entity);
		}
	}

	public List<Item> listAll(ContaApp contaApp) {
		return ItemDAO.listAll(contaApp);
	}

}
