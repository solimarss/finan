package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Tipo;
import br.com.solimar.finan.persistence.TipoDAO;

@Stateless
public class TipoRN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TipoDAO ItemDAO;

	public void insert(Tipo entity) {
		ItemDAO.insert(entity);
	}

	public void update(Tipo entity) {
		ItemDAO.update(entity);
	}

	public void save(Tipo entity) {
		if (entity.getId() == null) {
			System.out.println("INSERT");
			ItemDAO.insert(entity);
		} else {
			ItemDAO.update(entity);
		}
	}

	public List<Tipo> listAll(ContaApp contaApp) {
		return ItemDAO.listAll(contaApp);
	}

}
