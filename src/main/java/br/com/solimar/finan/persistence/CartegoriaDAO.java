package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.ContaApp;

public class CartegoriaDAO extends AbstractDao<Categoria> {

	private static final long serialVersionUID = 1L;

	public CartegoriaDAO() {
		super(Categoria.class);
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> listAll(ContaApp contaApp) {

		Query query = em.createQuery("Select O from Categoria O Where O.contaApp =:pContaApp", Categoria.class);

		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}

	public Categoria findById(Long id, ContaApp contaApp) {

		Query query = em.createQuery("Select O from Categoria O Where O.id =:pId AND O.contaApp =:pContaApp",
				Categoria.class);

		query.setParameter("pId", id);
		query.setParameter("pContaApp", contaApp);

		return (Categoria) query.getSingleResult();

	}

}
