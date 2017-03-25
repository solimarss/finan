package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.Fatura;

public class CartaoCreditoFaturaDAO extends AbstractDao<Fatura> {

	private static final long serialVersionUID = 1L;

	public CartaoCreditoFaturaDAO() {
		super(Fatura.class);
	}

	@SuppressWarnings("unchecked")
	public List<Fatura> findByVencimentoAndCartao(Fatura cartaoCreditoFatura) {

		Query query = em.createQuery(
				"Select c from CartaoCreditoFatura c Where "
				+ "c.dataVencimento =:pVencimento "
				+ "AND c.conta =:pCartao "
				+ "AND c.contaApp =:pContaApp",
				Fatura.class);

		query.setParameter("pVencimento", cartaoCreditoFatura.getDataVencimento());
		query.setParameter("pConta", cartaoCreditoFatura.getConta());
		query.setParameter("pContaApp", cartaoCreditoFatura.getContaApp());
		
		

		return query.getResultList();

	}

}
