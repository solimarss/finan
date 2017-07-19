package br.com.solimar.finan.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.solimar.finan.business.LancamentoFilters;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.DataUtil;
import br.com.solimar.finan.vo.ValueByGroup;

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

	public List<Lancamento> searchForDuplicityCreditCard(Lancamento lancamento) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.memo =:pMemo " + "AND O.valor =:pValor "
						+ "AND O.data =:pData AND O.cartaoCreditoFatura =:pFatura AND O.createdAt !=:pCreatedAt AND O.contaApp =:pContaApp",
				Lancamento.class);

		/*
		 * Query query = em.createQuery(
		 * "Select O from Lancamento O Where O.memo =:pMemo AND O.transactionId =:pTranId AND O.contaApp =:pContaApp"
		 * , Lancamento.class);
		 */

		query.setParameter("pMemo", lancamento.getMemo());
		query.setParameter("pValor", lancamento.getValor());
		query.setParameter("pData", lancamento.getData());
		query.setParameter("pContaApp", lancamento.getContaApp());
		query.setParameter("pFatura", lancamento.getCartaoCreditoFatura());

		// Para ser duplicado tem que ser numa data de criação diferente
		query.setParameter("pCreatedAt", lancamento.getCreatedAt());

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findNaoCategorizados(ContaApp contaApp, Date inicio, Date fim) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.categorizado =:pCategorizado AND O.contaApp =:pContaApp AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pCategorizado", false);
		query.setParameter("pContaApp", contaApp);

		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);

		// query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes,
		// ano));
		// query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes,
		// ano));

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findPossivelDuplicidade(ContaApp contaApp, Lancamento lancamento, Date inicio, Date fim) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.contaApp =:pContaApp AND O.data =:pData AND O.valor =:pValor  AND O.contaApp =:pContaApp AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pData", lancamento.getData());
		query.setParameter("pValor", lancamento.getValor());
		query.setParameter("pContaApp", lancamento.getContaApp());
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findSaidas(ContaApp contaApp, Date inicio, Date fim) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipoES =:pTipoES AND O.valorConsiderado =:pVConsiderado AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pVConsiderado", true);
		query.setParameter("pTipoES", LancamentoTipoEnum.S);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);
		return query.getResultList();

	}

	public List<Lancamento> search(LancamentoFilters filters, Date inicio, Date fim) {

		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Lancamento.class);

		Criteria tipo = null;
		if (filters.getCategoriaId() != null || filters.getClassificacao() != null) {
			tipo = criteria.createCriteria("tipo", "tipo");
		}

		if (filters.getCategoriaId() != null) {
			tipo.add(Restrictions.eq("categoria.id", filters.getCategoriaId()));
		}
		if (filters.getClassificacao() != null) {
			tipo.add(Restrictions.eq("classificacao", filters.getClassificacao()));
		}
		if (filters.getTipoES() != null) {
			criteria.add(Restrictions.eq("tipoES", filters.getTipoES()));
		}
		if (filters.getTipoId() != null) {
			criteria.add(Restrictions.eq("tipo.id", filters.getTipoId()));
		}
		if (filters.getVlrConsiderado() != null) {
			criteria.add(Restrictions.eq("valorConsiderado", filters.getVlrConsiderado()));

		}

		criteria.add(Restrictions.eq("categorizado", true));
		criteria.add(Restrictions.eq("contaApp", filters.getContaApp()));

		criteria.add(Restrictions.between("dataPagamento", inicio, fim));
		
		criteria.addOrder(Order.desc("data")).addOrder(Order.desc("dataPagamento")).addOrder(Order.desc("valor"));

		return criteria.list();

	}

	@SuppressWarnings("unchecked")
	public List<ValueByGroup> sumValorGroupByCategoria(LancamentoTipoEnum tipoES, ContaApp contaApp, Date inicio,
			Date fim) {

		Query query = em.createQuery(
				"select sum(O.valor), O.tipo.categoria.nome  from Lancamento O WHERE O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.categoria.nome ");

		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);
		List<Object[]> results = query.getResultList();

		List<ValueByGroup> valores = new ArrayList<>();

		for (Object[] result : results) {
			ValueByGroup v = new ValueByGroup();
			v.setValor((BigDecimal) result[0]);
			v.setGroupName(result[1].toString());
			valores.add(v);
		}

		return valores;

	}

	public BigDecimal sumValorLancamentos(LancamentoTipoEnum tipoES, ContaApp contaApp, Date inicio, Date fim) {

		Query query = em.createQuery(
				"select sum(O.valor) from Lancamento O WHERE O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) ");

		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);

		return (BigDecimal) query.getSingleResult();

	}

	@SuppressWarnings("unchecked")
	public List<ValueByGroup> sumValorGroupByTipo(LancamentoTipoEnum tipoES, ContaApp contaApp, Date inicio, Date fim) {

		Query query = em.createQuery(
				"select sum(O.valor), O.tipo.nome  from Lancamento O WHERE O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.nome ");

		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);

		List<Object[]> results = query.getResultList();

		List<ValueByGroup> valores = new ArrayList<>();

		for (Object[] result : results) {
			ValueByGroup v = new ValueByGroup();
			v.setValor((BigDecimal) result[0]);
			v.setGroupName(result[1].toString());
			valores.add(v);
		}

		return valores;

	}

	@SuppressWarnings("unchecked")
	public List<ValueByGroup> sumValorGroupByTipoByCategoria(String categoriaNome, LancamentoTipoEnum tipoES,
			ContaApp contaApp, Date inicio, Date fim) {

		Query query = em.createQuery(
				"select sum(O.valor) as total, O.tipo.nome  from Lancamento O WHERE O.tipo.categoria.nome =:pCategoriaNome AND O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.nome ORDER BY total");

		query.setParameter("pCategoriaNome", categoriaNome);
		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", inicio);
		query.setParameter("endDate", fim);
		List<Object[]> results = query.getResultList();

		List<ValueByGroup> valores = new ArrayList<>();

		for (Object[] result : results) {
			ValueByGroup v = new ValueByGroup();
			v.setValor((BigDecimal) result[0]);
			v.setGroupName(result[1].toString());
			valores.add(v);
		}

		return valores;

	}

}
