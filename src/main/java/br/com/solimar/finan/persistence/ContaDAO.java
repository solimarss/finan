package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.entity.ContaApp;

public class ContaDAO extends AbstractDao<Conta> {

	private static final long serialVersionUID = 1L;

	public ContaDAO() {
		super(Conta.class);
	}

	@SuppressWarnings("unchecked")
	public List<Conta> findByContaAndBanco(Conta contaBancaria) {

		/*Query query = em.createQuery(
				"Select c from ContaBancaria c Where c.contaNumero =:pConta AND c.agenciaNumero =:pAgencia AND c.contaApp =:pContaApp",
				ContaBancaria.class);*/
		Query query = em.createQuery(
				"Select c from Conta c Where c.contaNumero =:pConta AND c.bancoCodigo =:pBancoCod AND c.contaApp =:pContaApp",
				Conta.class);

		query.setParameter("pConta", contaBancaria.getContaNumero());
		query.setParameter("pBancoCod", contaBancaria.getBancoCodigo());
		//query.setParameter("pAgencia", contaBancaria.getAgenciaNumero());
		query.setParameter("pContaApp", contaBancaria.getContaApp());
		
		System.out.println("pConta "+contaBancaria.getContaNumero());
		//System.out.println("pAgencia "+ contaBancaria.getAgenciaNumero());
		System.out.println("pContaApp "+ contaBancaria.getContaApp());

		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Conta> listAll(ContaApp contaApp) {

		Query query = em.createQuery("Select O from Conta O Where O.contaApp =:pContaApp", Conta.class);

		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Conta> findLancamentoManual(ContaApp contaApp) {

		Query query = em.createQuery("Select O from Conta O Where O.lancamentoManual =:pManual AND O.contaApp =:pContaApp", Conta.class);

		query.setParameter("pContaApp", contaApp);
		query.setParameter("pManual", true);

		return query.getResultList();

	}

}
