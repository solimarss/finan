package br.com.solimar.finan.view.categorizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.CategoriaRN;
import br.com.solimar.finan.business.TipoRN;
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Tipo;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class CategorizacaoEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;

	@Inject
	private TipoRN itemRN;

	@Inject
	private CategoriaRN categoriaRN;

	@Inject
	private UserSession userSession;

	private Lancamento lancamento;
	private List<Tipo> itens;
	private List<Tipo> itensAll;
	private List<Categoria> categorias;
	private List<Categoria> categoriasReceita;
	private List<Categoria> categoriasDespesa;
	private boolean desconsiderarValor = false;
	private boolean enableDesconsiderarValor = true;
	private Long categoriaIdSelected;
	

	@Inject
	@On("categorizacao.save")
	Event<Lancamento> eventoCategorizacao;

	@PostConstruct
	private void init() {

		lancamento = new Lancamento();
		itensAll = itemRN.listAll(userSession.getContaApp());

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

	}

	public void onSelectCategoria() {
		itens = new ArrayList<>();
		if (categoriaIdSelected != null) {

			for (Tipo item : itensAll) {
				if (item.getCategoria().getId().equals(categoriaIdSelected)) {
					itens.add(item);
				}
			}
			enableDesconsiderarValor = false;
			desconsiderarValor = false;

		} else {
			enableDesconsiderarValor = true;
		}

	}

	public void onSelectDesconsiderarValor() {
		categoriaIdSelected = null;
		lancamento.getItem().setId(null);

	}

	public void abrirDialog(Lancamento lancamento) {
		this.lancamento = lancamento;
		lancamento.setDescricao(lancamento.getMemo());
		desconsiderarValor = false;
		enableDesconsiderarValor = true;
		categoriaIdSelected = null;
		lancamento.getItem().setId(null);
		

		if (lancamento.getTipo().equals(LancamentoTipoEnum.E)) {
			categorias = categoriasReceita;
		} else {
			categorias = categoriasDespesa;
		}
		UIService.update("categorizacao_edit_form_id");
		UIService.show("categorizacao_edit_wvar");
	}

	public void save() {

		try {

			if(desconsiderarValor){
				lancamento.setValorConsiderado(false);
				lancamento.setItem(null);
			}else{
				lancamento.setValorConsiderado(true);
			}
			
			lancamento.setContaApp(userSession.getContaApp());
			lancamento.setUpdatedAt(new Date());
			lancamento.setCategorizado(true);
			
			

			lancamentoRN.save(lancamento);
			eventoCategorizacao.fire(lancamento);

			UIService.hide("categorizacao_edit_wvar");
			UIService.showSuccess();

		} catch (Exception e) {
			UIService.showError(e);
		}

	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public boolean isDesconsiderarValor() {
		return desconsiderarValor;
	}

	public void setDesconsiderarValor(boolean desconsiderarValor) {
		this.desconsiderarValor = desconsiderarValor;
	}

	public List<Tipo> getItens() {
		return itens;
	}

	public void setItens(List<Tipo> itens) {
		this.itens = itens;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Long getCategoriaIdSelected() {
		return categoriaIdSelected;
	}

	public void setCategoriaIdSelected(Long categoriaIdSelected) {
		this.categoriaIdSelected = categoriaIdSelected;
	}

	public boolean isEnableDesconsiderarValor() {
		return enableDesconsiderarValor;
	}

	public void setEnableDesconsiderarValor(boolean enableDesconsiderarValor) {
		this.enableDesconsiderarValor = enableDesconsiderarValor;
	}

}