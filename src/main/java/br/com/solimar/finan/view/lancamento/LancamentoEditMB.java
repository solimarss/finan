package br.com.solimar.finan.view.lancamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.CategoriaRN;
import br.com.solimar.finan.business.ContaRN;
import br.com.solimar.finan.business.TipoRN;
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Categoria;
import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.entity.Tipo;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancTipoListagemEnum;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.util.GeradorCodigo;
import br.com.solimar.finan.view.JSFUtil;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class LancamentoEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;

	@Inject
	private TipoRN itemRN;

	@Inject
	private UserSession userSession;

	@Inject
	private CategoriaRN categoriaRN;

	@Inject
	private ContaRN contaRN;

	private List<Conta> contas;
	private Lancamento lancamento;
	private List<Tipo> itens;
	private List<Tipo> itensAll;
	private List<Categoria> categorias;
	private List<Categoria> categoriasReceita;
	private List<Categoria> categoriasDespesa;
	private boolean desconsiderarValor = false;
	private boolean enableDesconsiderarValor = true;
	private Long categoriaIdSelected;
	private Long contaIdSelected;
	private boolean edicao = false;

	@Inject
	@On("entrada.save")
	Event<Lancamento> eventoCategorizacao;

	@PostConstruct
	private void init() {

		lancamento = new Lancamento();
		itensAll = itemRN.listAll(userSession.getContaApp());
		contas = contaRN.findLancamentoManual(userSession.getContaApp());

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

			if (lancamento.getTipo() == null) {
				lancamento.setTipo(new Tipo());
			}

		} else {
			enableDesconsiderarValor = true;
		}

	}

	public void onSelectDesconsiderarValor() {

		if (desconsiderarValor) {
			categoriaIdSelected = null;
			if (lancamento.getTipo() != null) {
				lancamento.getTipo().setId(null);

			}

		} else {

		}

	}
	
	public boolean disableCampoDesconsiderarValor() {
		if (enableDesconsiderarValor || lancamento.getIsTransferencia()) {
			return true;
		}
		return false;
		
	}

	public void abrirDialog(Lancamento lancamento) {
		edicao = true;
		this.lancamento = lancamento;

		if (lancamento.getTipo() != null) {
			categoriaIdSelected = lancamento.getTipo().getCategoria().getId();

		}

		onSelectCategoria();

		desconsiderarValor = !lancamento.isValorConsiderado();

		if (lancamento.getTipoES().equals(LancamentoTipoEnum.E)) {
			categorias = categoriasReceita;
		} else {
			categorias = categoriasDespesa;
		}

		UIService.update("lancamento_edit_form_id");
		UIService.show("lancamento_edit_wvar");
	}

	public void abrirDialogNew() {

		lancamento = new Lancamento();
		lancamento.setTipo(new Tipo());
		lancamento.getTipo().setId(0l);

		if (userSession.getListagem().equals(LancTipoListagemEnum.E)) {
			categorias = categoriasReceita;
			lancamento.setTipoES(LancamentoTipoEnum.E);

		} else {
			categorias = categoriasDespesa;
			lancamento.setTipoES(LancamentoTipoEnum.S);

		}

		edicao = false;
		categoriaIdSelected = null;
		desconsiderarValor = false;
		contaIdSelected = null;

		UIService.update("lancamento_edit_form_id");
		UIService.show("lancamento_edit_wvar");
	}

	public void save() {

		try {

			if (lancamento.getIsTransferencia()) {

			} else {

				if (!edicao) {
					lancamento.setCategorizado(true);
					lancamento.setConta(new Conta(contaIdSelected));
					lancamento.setCodigo(GeradorCodigo.gerar());
					lancamento.setDataPagamento(lancamento.getData());
					lancamento.setUpdatedAt(new Date());
					lancamento.setCreatedAt(new Date());

					System.out.println("data pagto; " + lancamento.getDataPagamento());

					if (lancamento.getTipoES().equals(LancamentoTipoEnum.S)) {
						lancamento.setValor(turnToNegative(lancamento.getValor()));
					}

				}

				lancamento.setContaApp(userSession.getContaApp());
				lancamento.setUpdatedAt(new Date());
				lancamento.setCategorizado(true);
				lancamento.setValorConsiderado(!desconsiderarValor);
				lancamento.setIsTransferencia(false);

				if (desconsiderarValor) {
					lancamento.setTipo(null);
				}

			}

			
			lancamentoRN.save(lancamento);
			eventoCategorizacao.fire(lancamento);

			UIService.hide("lancamento_edit_wvar");
			UIService.showSuccess();

		} catch (Exception e) {
			UIService.showError(e);
		}

	}

	public String tipoLancamentoView() {
		return userSession.getListagem().getDescricao();

	}

	private BigDecimal turnToNegative(BigDecimal valor) {
		if (valor.signum() == 1) {
			return valor.multiply(new BigDecimal(-1));
		}
		return valor;
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

	public boolean isEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public boolean isEnableDesconsiderarValor() {
		return enableDesconsiderarValor;
	}

	public void setEnableDesconsiderarValor(boolean enableDesconsiderarValor) {
		this.enableDesconsiderarValor = enableDesconsiderarValor;
	}

	public Long getCategoriaIdSelected() {
		return categoriaIdSelected;
	}

	public void setCategoriaIdSelected(Long categoriaIdSelected) {
		this.categoriaIdSelected = categoriaIdSelected;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Long getContaIdSelected() {
		return contaIdSelected;
	}

	public void setContaIdSelected(Long contaIdSelected) {
		this.contaIdSelected = contaIdSelected;
	}

}