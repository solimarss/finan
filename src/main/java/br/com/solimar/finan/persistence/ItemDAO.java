package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Item;

public class ItemDAO extends AbstractDao<Item> {

	private static final long serialVersionUID = 1L;

	public ItemDAO() {
		super(Item.class);
	}

	@SuppressWarnings("unchecked")
	public List<Item> listAll(ContaApp contaApp) {

		Query query = em.createQuery("Select O from Item O Where O.contaApp =:pContaApp", Item.class);

		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}

	

}
