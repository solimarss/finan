package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.persistence.LancamentoDAO;

@Stateless
public class LancamentoRN implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private  LancamentoDAO lancamentoDAO;
	
	public void insert(Lancamento entity) {
		lancamentoDAO.insert(entity);
	}
	
	public List<Lancamento> findByMemoAndTransactionIdAndContaApp(Lancamento lancamento) {
		return lancamentoDAO.findByMemoAndTransactionIdAndContaApp(lancamento);
	}

}
