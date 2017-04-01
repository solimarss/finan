package br.com.solimar.finan.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.persistence.LancamentoDAO;
import br.com.solimar.finan.vo.ValueByGroup;

@Stateless
public class LancamentoRN implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LancamentoDAO lancamentoDAO;

	public void insert(Lancamento entity) {
		lancamentoDAO.insert(entity);
	}

	public List<Lancamento> findByMemoAndTransactionIdAndContaApp(Lancamento lancamento) {
		return lancamentoDAO.findByMemoAndTransactionIdAndContaApp(lancamento);
	}

	public List<Lancamento> findNaoCategorizados(ContaApp contaApp,int mes, int ano) {
		return lancamentoDAO.findNaoCategorizados(contaApp,mes,ano);
	}

	public List<Lancamento> findEntradas(ContaApp contaApp,int mes, int ano) {
		return lancamentoDAO.findEntradas(contaApp, mes, ano);
	}

	public List<Lancamento> findSaidas(ContaApp contaApp,int mes, int ano) {
		return lancamentoDAO.findSaidas(contaApp, mes, ano);
	}

	public void save(Lancamento entity) {
		if (entity.getId() == null) {
			lancamentoDAO.insert(entity);
		} else {
			lancamentoDAO.update(entity);
		}
	}
	public List<ValueByGroup> sumValorGroupByCategoria(LancamentoTipoEnum tipoEs, ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.sumValorGroupByCategoria(tipoEs, contaApp, mes, ano);
	}
	public List<ValueByGroup> sumValorGroupByTipo(LancamentoTipoEnum tipoEs, ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.sumValorGroupByTipo(tipoEs, contaApp, mes, ano);
	}
}
