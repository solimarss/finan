package br.com.solimar.finan.view.entrada;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.ItemRN;
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Item;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class EntradaEditMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;
	
	@Inject
	private ItemRN itemRN;
	
	@Inject
	private UserSession userSession;

	private Lancamento lancamento;
	private List<Item> itens;
	private List<Item> itensReceita;
	private List<Item> itensDespesa;
	private boolean desconsiderarValor = false;
	private boolean edicao = false;

	@Inject
	@On("entrada.save")
	Event<Lancamento> eventoCategorizacao;

	@PostConstruct
	private void init() {
		lancamento = new Lancamento();
		itens = itemRN.listAll(userSession.getContaApp());
		itensReceita = new ArrayList<>();
		itensDespesa = new ArrayList<>();
		for(Item item: itens){
			if(item.getCategoria().getTipo().equals(LancamentoTipoEnum.E)){
				itensReceita.add(item);
			}else{
				itensDespesa.add(item);
			}
		}

	}

	public void abrirDialog(Lancamento lancamento) {
		edicao = true;
		this.lancamento = lancamento;
		desconsiderarValor = !lancamento.isValorConsiderado();
		if(lancamento.getTipo().equals(LancamentoTipoEnum.E)){
			itens = itensReceita;
		}
		else{
			itens = itensDespesa;
		}
		UIService.update("entrada_edit_form_id");
		UIService.show("entrada_edit_wvar");
	}
	
	
	public void abrirDialogNew() {
		edicao = false;
		this.lancamento = new Lancamento();
		desconsiderarValor = false;
		
		UIService.update("entrada_edit_form_id");
		UIService.show("entrada_edit_wvar");
	}

	public void save() {

		try {

			if(!edicao){
				lancamento.setTipo(LancamentoTipoEnum.E);
				lancamento.setCategorizado(true);
			}
			
			lancamento.setContaApp(userSession.getContaApp());
			lancamento.setUpdatedAt(new Date());
			lancamento.setCategorizado(true);
			lancamento.setValorConsiderado(!desconsiderarValor);
			
			
			lancamentoRN.save(lancamento);
			eventoCategorizacao.fire(lancamento);

			UIService.hide("entrada_edit_wvar");
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

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public boolean isEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}

}