package br.com.solimar.finan.view.entrada;

import java.io.Serializable;
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
import br.com.solimar.finan.view.JSFUtil;
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

	private String tipoLancamentoView;

	private Long categoriaIdSelected;

	@Inject
	private CategoriaRN categoriaRN;

	private List<Categoria> categorias;
	private List<Categoria> categoriasReceita;
	private List<Categoria> categoriasDespesa;

	@PostConstruct
	private void init() {

		tipoLancamentoView = JSFUtil.getUrlParameter("tipo");

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

		if (tipoLancamentoView.equals("receita")) {
			categorias = categoriasReceita;

		} else {
			categorias = categoriasDespesa;

		}

		search();
	}

	public void search() {
		if (tipoLancamentoView.equals("receita")) {
			lancamentos = lancamentoRN.findEntradas(userSession.getContaApp(), userSession.getMes(),
					userSession.getAno());
		} else {
			lancamentos = lancamentoRN.findSaidas(userSession.getContaApp(), categoriaIdSelected, userSession.getMes(),
					userSession.getAno());
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
		if (tipoLancamentoView.equals("receita")) {
			return "Receitas";
		}
		if (tipoLancamentoView.equals("despesa")) {
			return "Despesas";
		}
		return "";
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

}