package br.com.solimar.finan.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.entity.Padrao;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.DataUtil;
import br.com.solimar.finan.vo.ValueByGroup;

public class PadraoDAO extends AbstractDao<Padrao> {

	private static final long serialVersionUID = 1L;

	public PadraoDAO() {
		super(Padrao.class);
	}

	@SuppressWarnings("unchecked")
	public List<Padrao> findByMemo(String memo, ContaApp contaApp) {

		Query query = em.createQuery(
				"Select O from Padrao O Where O.memo =:pMemo AND O.contaApp =:pContaApp",
				Padrao.class);

		query.setParameter("pMemo", memo);
		query.setParameter("pContaApp", contaApp);

		return query.getResultList();

	}

}