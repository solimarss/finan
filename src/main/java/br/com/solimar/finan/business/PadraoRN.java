package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.entity.Padrao;
import br.com.solimar.finan.persistence.PadraoDAO;

@Stateless
public class PadraoRN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PadraoDAO padraoDAO;

	public void insert(Padrao entity) {
		padraoDAO.insert(entity);
	}


	public void save(Padrao entity) {
		if (entity.getId() == null) {
			padraoDAO.insert(entity);
		} else {
			padraoDAO.update(entity);
		}
	}
	
	public List<Padrao> findByMemo(String memo, ContaApp contaApp) {
		return padraoDAO.findByMemo(memo, contaApp);
	}
}
