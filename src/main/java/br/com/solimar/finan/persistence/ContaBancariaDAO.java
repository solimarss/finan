package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.Conta;

public class ContaBancariaDAO extends AbstractDao<Conta> {

	private static final long serialVersionUID = 1L;

	public ContaBancariaDAO() {
		super(Conta.class);
	}

	@SuppressWarnings("unchecked")
	public List<Conta> findByContaAndBanco(Conta contaBancaria) {

		/*Query query = em.createQuery(
				"Select c from ContaBancaria c Where c.contaNumero =:pConta AND c.agenciaNumero =:pAgencia AND c.contaApp =:pContaApp",
				ContaBancaria.class);*/
		Query query = em.createQuery(
				"Select c from ContaBancaria c Where c.contaNumero =:pConta AND c.bancoCodigo =:pBancoCod AND c.contaApp =:pContaApp",
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

}
