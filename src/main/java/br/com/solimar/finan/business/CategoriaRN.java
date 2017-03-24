package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.persistence.CartegoriaDAO;

@Stateless
public class CategoriaRN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CartegoriaDAO cartegoriaDAO;

	public void insert(Categoria entity) {
		cartegoriaDAO.insert(entity);
	}

	public void update(Categoria entity) {
		cartegoriaDAO.update(entity);
	}

	public void save(Categoria entity) {
		if (entity.getId() == null) {
			System.out.println("INSERT");
			cartegoriaDAO.insert(entity);
		} else {
			cartegoriaDAO.update(entity);
		}
	}

	public List<Categoria> listAll(ContaApp contaApp) {
		return cartegoriaDAO.listAll(contaApp);
	}

	public Categoria findById(Long id, ContaApp contaApp) {
		return cartegoriaDAO.findById(id, contaApp);
	}

}
