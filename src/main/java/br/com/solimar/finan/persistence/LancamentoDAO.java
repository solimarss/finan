package br.com.solimar.finan.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.Categoria;
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

	@SuppressWarnings("unchecked")
	public List<Lancamento> findNaoCategorizados(ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.categorizado =:pCategorizado AND O.contaApp =:pContaApp AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pCategorizado", false);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findEntradas(ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipoES =:pTipoES AND O.valorConsiderado =:pVConsiderado AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pVConsiderado", true);
		query.setParameter("pTipoES", LancamentoTipoEnum.E);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Lancamento> findSaidas(ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipoES =:pTipoES AND O.valorConsiderado =:pVConsiderado AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pVConsiderado", true);
		query.setParameter("pTipoES", LancamentoTipoEnum.S);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> findSaidas(ContaApp contaApp, Categoria categoria, int mes, int ano) {

		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipoES =:pTipoES AND O.valorConsiderado =:pVConsiderado AND O.tipo.categoria =:pCategoria AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pVConsiderado", true);
		query.setParameter("pTipoES", LancamentoTipoEnum.S);
		query.setParameter("pCategorizado", true);
		query.setParameter("pCategoria", categoria);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<ValueByGroup> sumValorGroupByCategoria(LancamentoTipoEnum tipoES, ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"select sum(O.valor), O.tipo.categoria.nome  from Lancamento O WHERE O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.categoria.nome ");

		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

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
	public List<ValueByGroup> sumValorGroupByTipo(LancamentoTipoEnum tipoES, ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"select sum(O.valor), O.tipo.nome  from Lancamento O WHERE O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.nome ");

		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

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
	public List<ValueByGroup> sumValorGroupByTipoByCategoria(String categoriaNome, LancamentoTipoEnum tipoES, ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery(
				"select sum(O.valor) as total, O.tipo.nome  from Lancamento O WHERE O.tipo.categoria.nome =:pCategoriaNome AND O.tipoES =:pTipoES AND O.valorConsiderado =:pValorConsid AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate) GROUP BY O.tipo.nome ORDER BY total");

		query.setParameter("pCategoriaNome", categoriaNome);
		query.setParameter("pTipoES", tipoES);
		query.setParameter("pValorConsid", true);
		query.setParameter("pCategorizado", true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

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
