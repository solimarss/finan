package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.ContaBancaria;

public class ContaBancariaDAO extends AbstractDao<ContaBancaria> {

	private static final long serialVersionUID = 1L;

	public ContaBancariaDAO() {
		super(ContaBancaria.class);
	}

	public List<ContaBancaria> findByAgenciaAndConta(ContaBancaria contaBancaria) {

		Query query = em.createQuery(
				"Select c from ContaBancaria c Where c.contaNumero =:pConta AND c.agenciaNumero =:pAgencia AND c.contaApp =:pContaApp",
				ContaBancaria.class);

		query.setParameter("pConta", contaBancaria.getContaNumero());
		query.setParameter("pAgencia", contaBancaria.getAgenciaNumero());
		query.setParameter("pContaApp", contaBancaria.getContaApp());

		return query.getResultList();

	}

}
