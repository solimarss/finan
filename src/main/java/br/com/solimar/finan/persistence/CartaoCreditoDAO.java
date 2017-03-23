package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.CartaoCredito;

public class CartaoCreditoDAO extends AbstractDao<CartaoCredito> {

	private static final long serialVersionUID = 1L;

	public CartaoCreditoDAO() {
		super(CartaoCredito.class);
	}

	@SuppressWarnings("unchecked")
	public List<CartaoCredito> findByNumero(CartaoCredito cartaoCredito) {

		Query query = em.createQuery(
				"Select c from CartaoCredito c Where c.numero =:pNumero AND c.contaApp =:pContaApp",
				CartaoCredito.class);

		query.setParameter("pNumero", cartaoCredito.getNumero());
		query.setParameter("pContaApp", cartaoCredito.getContaApp());
		
		

		return query.getResultList();

	}

}
