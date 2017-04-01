package br.com.solimar.finan.persistence;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.DataUtil;

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
		query.setParameter("pCategorizado",true);
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
		query.setParameter("pCategorizado",true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> countGroupByCategoria(LancamentoTipoEnum tipoES, ContaApp contaApp, int mes, int ano) {

		Query query = em.createQuery("select sum(O.valor), O.tipo.categoria.nome  from Lancamento O GROUP BY O.tipo.categoria.nome ");
		
		//query.setParameter("pTipoES", tipoES);
		List<Object[]> results  = query.getResultList();
        
		 System.out.println("SUM: "+ query.getResultList());
		
		for (Object[] result : results) {
		   // String name = (String) result[0];
		  //  int count = ((Number) result[1]).intValue();
			
			// int count = ((Number) result[0]).intValue();
			// BigDecimal b = (BigDecimal) result[1];
		    System.out.println("SUM: "+result[0]);
		    System.out.println("SUM: "+result[1]);
		}
		
       
        
      
        
		/*List<Object[]> results = entityManager
		        .createQuery("SELECT O.valor AS name, COUNT(m) AS total FROM Man AS m GROUP BY m.name ORDER BY m.name ASC");
		        .getResultList();
		        
		for (Object[] result : results) {
		    String name = (String) result[0];
		    int count = ((Number) result[1]).intValue();
		}
		
		  
		
		Query query = em.createQuery(
				"Select O from Lancamento O Where O.tipo =:pTipo AND O.valorConsiderado =:pVConsiderado AND O.categorizado =:pCategorizado AND O.contaApp =:pContaApp  AND (O.dataPagamento BETWEEN :startDate AND :endDate)",
				Lancamento.class);

		query.setParameter("pVConsiderado", true);
		query.setParameter("pTipo", LancamentoTipoEnum.S);
		query.setParameter("pCategorizado",true);
		query.setParameter("pContaApp", contaApp);
		query.setParameter("startDate", DataUtil.getFirstDayOfTheMonth(mes, ano));
		query.setParameter("endDate", DataUtil.getLastDayOfTheMonth(mes, ano));

		return query.getResultList();*/
        return null;
	}
	
}
