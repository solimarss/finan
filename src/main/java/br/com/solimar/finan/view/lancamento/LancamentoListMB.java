package br.com.solimar.finan.view.lancamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.CategoriaRN;
import br.com.solimar.finan.business.LancamentoFilters;
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.business.TipoRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.entity.Tipo;
import br.com.solimar.finan.enums.LancTipoListagemEnum;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.enums.TipoClassificacaoEnum;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class LancamentoListMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;

	@Inject
	private TipoRN tipoRN;

	@Inject
	private UserSession userSession;

	private List<Lancamento> lancamentos;

	private Long categoriaIdSelected;
	private Long tipoIdSelected;
	private TipoClassificacaoEnum classificacaoSelected;
	private BigDecimal total = BigDecimal.ZERO;

	@Inject
	private CategoriaRN categoriaRN;

	private List<Categoria> categorias;
	private List<Categoria> categoriasReceita;
	private List<Categoria> categoriasDespesa;
	private List<Tipo> tipos;
	private List<Tipo> tiposAll;
	private LancTipoListagemEnum listagem;

	@PostConstruct
	private void init() {
		listagem = userSession.getListagem();

		categoriasDespesa = new ArrayList<>();
		categoriasReceita = new ArrayList<>();
		tipos = new ArrayList<>();
		;
		tiposAll = new ArrayList<>();
		;

		tiposAll = tipoRN.listAll(userSession.getContaApp());
		categorias = categoriaRN.listAll(userSession.getContaApp());

		// TODO refatora, buscar listagem do banco, não filtrar aqui no código
		for (Categoria categoria : categorias) {
			if (categoria.getTipo().equals(LancamentoTipoEnum.E)) {
				categoriasReceita.add(categoria);
			} else {
				categoriasDespesa.add(categoria);
			}
		}

		if (listagem.equals(LancTipoListagemEnum.S)) {
			categorias = categoriasDespesa;
		} else if (listagem.equals(LancTipoListagemEnum.E)) {
			categorias = categoriasReceita;
		}

		search();
	}

	public void search() {
		LancamentoFilters filters = new LancamentoFilters();
		filters.setContaApp(userSession.getContaApp());
		filters.setMes(userSession.getMes());
		filters.setAno(userSession.getAno());
		filters.setCategoriaId(categoriaIdSelected);
		filters.setClassificacao(classificacaoSelected);
		filters.setTipoId(tipoIdSelected);

		if (listagem.equals(LancTipoListagemEnum.E)) {
			filters.setVlrConsiderado(true);
			filters.setTipoES(LancamentoTipoEnum.E);
		} else if (listagem.equals(LancTipoListagemEnum.S)) {
			filters.setVlrConsiderado(true);
			filters.setTipoES(LancamentoTipoEnum.S);
		} else if (listagem.equals(LancTipoListagemEnum.X)) {
			filters.setVlrConsiderado(null);
			filters.setTipoES(null);
			filters.setConta(userSession.getConta());
		} else {
			filters.setVlrConsiderado(null);
		}

		lancamentos = lancamentoRN.search(filters);

		total = BigDecimal.ZERO;
		for (Lancamento lancamento : lancamentos) {
			total = total.add(lancamento.getValor());
		}

	}

	public void excluir(Lancamento lancamento) {
		lancamentoRN.delete(lancamento);
		search();
		UIService.update("lancamento_list_form_id");

	}

	public void onSelectFilterCategoria() {

		// TODO implementar pesquisa por tipo

		if (categoriaIdSelected != null) {

			for (Tipo tipo : tiposAll) {
				if (tipo.getCategoria().getId().equals(categoriaIdSelected)) {
					tipos.add(tipo);
				}
			}

		}

		search();
		UIService.update("lancamento_list_form_id");
	}

	public void onSelectFilterTipo() {
		search();
		UIService.update("lancamento_list_form_id");
	}

	protected void onSave(@Observes @On("entrada.save") Lancamento evento) {
		search();
		UIService.update("lancamento_list_form_id");
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String tipoLancamentoView() {
		return listagem.getDescricao();

	}

	public Long getCategoriaIdSelected() {
		return categoriaIdSelected;
	}

	public void setCategoriaIdSelected(Long categoriaIdSelected) {
		this.categoriaIdSelected = categoriaIdSelected;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public TipoClassificacaoEnum[] getClassificacoes() {
		return TipoClassificacaoEnum.values();
	}

	public TipoClassificacaoEnum getClassificacaoSelected() {
		return classificacaoSelected;
	}

	public void setClassificacaoSelected(TipoClassificacaoEnum classificacaoSelected) {
		this.classificacaoSelected = classificacaoSelected;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}

	public Long getTipoIdSelected() {
		return tipoIdSelected;
	}

	public void setTipoIdSelected(Long tipoIdSelected) {
		this.tipoIdSelected = tipoIdSelected;
	}

	public LancTipoListagemEnum getListagem() {
		return listagem;
	}

	public void setListagem(LancTipoListagemEnum listagem) {
		this.listagem = listagem;
	}

}