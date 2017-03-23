package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.CartaoCreditoFatura;

public class CartaoCreditoFaturaDAO extends AbstractDao<CartaoCreditoFatura> {

	private static final long serialVersionUID = 1L;

	public CartaoCreditoFaturaDAO() {
		super(CartaoCreditoFatura.class);
	}

	@SuppressWarnings("unchecked")
	public List<CartaoCreditoFatura> findByVencimentoAndCartao(CartaoCreditoFatura cartaoCreditoFatura) {

		Query query = em.createQuery(
				"Select c from CartaoCreditoFatura c Where "
				+ "c.dataVencimento =:pVencimento "
				+ "AND c.cartao =:pCartao "
				+ "AND c.contaApp =:pContaApp",
				CartaoCreditoFatura.class);

		query.setParameter("pVencimento", cartaoCreditoFatura.getDataVencimento());
		query.setParameter("pCartao", cartaoCreditoFatura.getCartao());
		query.setParameter("pContaApp", cartaoCreditoFatura.getContaApp());
		
		

		return query.getResultList();

	}

}
