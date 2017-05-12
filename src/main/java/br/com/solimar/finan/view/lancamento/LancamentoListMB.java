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
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Lancamento;
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
	private UserSession userSession;

	private List<Lancamento> lancamentos;

	private Long categoriaIdSelected;
	private TipoClassificacaoEnum classificacaoSelected;
	private BigDecimal total = BigDecimal.ZERO;

	@Inject
	private CategoriaRN categoriaRN;

	private List<Categoria> categorias;
	private List<Categoria> categoriasReceita;
	private List<Categoria> categoriasDespesa;

	@PostConstruct
	private void init() {

		categorias = categoriaRN.listAll(userSession.getContaApp());
		categoriasDespesa = new ArrayList<>();
		categoriasReceita = new ArrayList<>();

		for (Categoria categoria : categorias) {
			if (categoria.getTipo().equals(LancamentoTipoEnum.E)) {
				categoriasReceita.add(categoria);
			} else {
				categoriasDespesa.add(categoria);
			}
		}

		if (userSession.isTipoESReceita()) {
			categorias = categoriasReceita;

		} else {
			categorias = categoriasDespesa;

		}

		search();
	}

	public void search() {
		if (userSession.isTipoESReceita()) {
			lancamentos = lancamentoRN.findEntradas(userSession.getContaApp(), userSession.getMes(),
					userSession.getAno());
		} else {
			lancamentos = lancamentoRN.findSaidas(userSession.getContaApp(), categoriaIdSelected, classificacaoSelected,
					userSession.getMes(), userSession.getAno());
		}
		total = BigDecimal.ZERO;
		for (Lancamento lancamento : lancamentos) {
			total = total.add(lancamento.getValor());
		}

	}

	public void onSelectFilterCategoria() {
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
		if (userSession.isTipoESReceita()) {
			return "Receitas";
		}
		return "Despesas";

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

}