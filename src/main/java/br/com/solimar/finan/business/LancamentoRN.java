package br.com.solimar.finan.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.solimar.finan.entity.Categoria;
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
	
	public void update(Lancamento entity) {
		lancamentoDAO.update(entity);
	}

	public List<Lancamento> findByMemoAndTransactionIdAndContaApp(Lancamento lancamento) {
		return lancamentoDAO.findByMemoAndTransactionIdAndContaApp(lancamento);
	}

	public List<Lancamento> findNaoCategorizados(ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.findNaoCategorizados(contaApp, mes, ano);
	}

	public List<Lancamento> findEntradas(ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.findEntradas(contaApp, mes, ano);
	}

	public BigDecimal sumValorEntrada(ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.sumValorLancamentos(LancamentoTipoEnum.E, contaApp, mes, ano);
	}
	
	public List<Lancamento> findDuplicados(Lancamento lancamento, int mes, int ano) {
		return lancamentoDAO.findPossivelDuplicidade(lancamento, mes, ano);
	}
	
	
	public List<Lancamento> findDuplicados(ContaApp contaApp, int mes, int ano) {

		List<Lancamento> lacamentosNaoCategorizados = lancamentoDAO.findNaoCategorizados(contaApp, mes, ano);

		List<Lancamento> lacamentosDuplicados = new ArrayList<>();

		for (Lancamento lancamento : lacamentosNaoCategorizados) {
			List<Lancamento> lancamentos = lancamentoDAO.findPossivelDuplicidade(lancamento, mes, ano);
			if (lancamentos.size() > 1) {
				for (Lancamento lanc : lancamentos) {

					// verificar se já está na lista
					boolean exite = false;
					for (Lancamento lanc2 : lacamentosDuplicados) {
						if (lanc2.getId().equals(lanc.getId())) {
							exite = true;
						}
					}
					// Se não existir adiciona
					if (!exite) {
						lacamentosDuplicados.add(lanc);
					}

				}
			}

		}

		System.out.println("lacamentosDuplicados.size(): " + lacamentosDuplicados.size());
		return lacamentosDuplicados;

	}

	public List<Lancamento> findSaidas(ContaApp contaApp, Long categoriaId, int mes, int ano) {
		if (categoriaId == null) {
			return lancamentoDAO.findSaidas(contaApp, mes, ano);
		} else {
			return lancamentoDAO.findSaidas(contaApp, new Categoria(categoriaId), mes, ano);
		}
	}

	public void save(Lancamento entity) {
		if (entity.getId() == null) {
			lancamentoDAO.insert(entity);
		} else {
			lancamentoDAO.update(entity);
		}
	}

	public List<ValueByGroup> sumValorGroupByCategoria(LancamentoTipoEnum tipoES, ContaApp contaApp, int mes, int ano) {

		List<ValueByGroup> groupByCategoria = lancamentoDAO.sumValorGroupByCategoria(tipoES, contaApp, mes, ano);
		for (ValueByGroup groupCat : groupByCategoria) {
			groupCat.setSubGroup((lancamentoDAO.sumValorGroupByTipoByCategoria(groupCat.getGroupName(), tipoES,
					contaApp, mes, ano)));

		}

		return groupByCategoria;
	}

	public List<ValueByGroup> sumValorGroupByTipo(LancamentoTipoEnum tipoEs, ContaApp contaApp, int mes, int ano) {
		return lancamentoDAO.sumValorGroupByTipo(tipoEs, contaApp, mes, ano);
	}

	public void delete(Lancamento lancamento) {
		lancamentoDAO.delete(lancamento.getId());
	}
}
