package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;

public class LancamentoDAO extends AbstractDao<Lancamento> {

	private static final long serialVersionUID = 1L;

	public LancamentoDAO() {
		super(Lancamento.class);
	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findByMemoAndTransactionIdAndContaApp(Lancamento lancamento) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where " + "O.memo =:pMemo " + "AND O.valor =:pValor "
						+ "AND O.data =:pData " + "AND O.transactionId =:pTranId " + "AND O.contaApp =:pContaApp",
				Lancamento.class);

		/*
		 * Query query = em.createQuery(
		 * "Select O from Lancamento O Where O.memo =:pMemo AND O.transactionId =:pTranId AND O.contaApp =:pContaApp"
		 * , Lancamento.class);
		 */

		query.setParameter("pMemo", lancamento.getMemo());
		query.setParameter("pValor", lancamento.getValor());
		query.setParameter("pData", lancamento.getData());
		query.setParameter("pTranId", lancamento.getTransactionId());
		query.setParameter("pContaApp", lancamento.getContaApp());

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findNaoCategorizados(ContaApp contaApp) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.categorizado =:pCategorizado AND O.contaApp =:pContaApp",
				Lancamento.class);

		
		query.setParameter("pCategorizado", false);
		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> findEntradas(ContaApp contaApp) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipo =:pTipo AND O.contaApp =:pContaApp",
				Lancamento.class);

		
		query.setParameter("pTipo", LancamentoTipoEnum.E);
		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> findSaidas(ContaApp contaApp) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipo =:pTipo AND O.contaApp =:pContaApp",
				Lancamento.class);

		
		query.setParameter("pTipo", LancamentoTipoEnum.S);
		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}
}
