package br.com.solimar.finan.persistence;

import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Tipo;

public class TipoDAO extends AbstractDao<Tipo> {

	private static final long serialVersionUID = 1L;

	public TipoDAO() {
		super(Tipo.class);
	}

	@SuppressWarnings("unchecked")
	public List<Tipo> listAll(ContaApp contaApp) {

		Query query = em.createQuery("Select O from Tipo O Where O.contaApp =:pContaApp", Tipo.class);

		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}

	

}
