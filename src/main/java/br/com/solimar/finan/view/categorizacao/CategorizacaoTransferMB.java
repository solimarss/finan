package br.com.solimar.finan.view.categorizacao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.solimar.finan.business.ContaRN;
import br.com.solimar.finan.business.LancamentoRN;
import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.entity.Lancamento;
import br.com.solimar.finan.view.On;
import br.com.solimar.finan.view.application.UIService;
import br.com.solimar.finan.view.application.UserSession;

@Named
@ViewScoped
public class CategorizacaoTransferMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private LancamentoRN lancamentoRN;

	// @Inject
	// private PadraoRN padraoRN;
	
	@Inject
	private ContaRN contaRN;

	@Inject
	private UserSession userSession;

	private Lancamento lancamento;
	private List<Conta> contas;
	private Long contaIdSelected;

	@Inject
	@On("categorizacao.save")
	Event<Lancamento> eventoCategorizacao;

	@PostConstruct
	private void init() {

		lancamento = new Lancamento();
		contas = contaRN.listAll(userSession.getContaApp());
		
		

	}

	public void abrirDialog(Lancamento lancamento) {
		this.lancamento = lancamento;
		lancamento.setDescricao(lancamento.getMemo());
		excluirContaDoLancamento(lancamento);
		UIService.update("categorizacao_transfer_form_id");
		UIService.show("categorizacao_transfer_wvar");
	}
	
	public void excluirContaDoLancamento(Lancamento lancamento) {
		contas = contaRN.listAll(userSession.getContaApp());
		for (Iterator<Conta> iterator = contas.iterator(); iterator.hasNext();) {
			Conta conta = (Conta) iterator.next();
			if(conta.getId().equals(lancamento.getConta().getId())) {
				iterator.remove();
			}
		}
	}
	
	
	
	private Conta findConta(Long idConta) {
		for (Conta conta : contas) {
			if(idConta.equals(conta.getId())) {
				return conta;
			}
		}
		return null;
	}

	public void save() {

		try {
			
			lancamento.setContaApp(userSession.getContaApp());
			lancamentoRN.saveTransfer(lancamento, findConta(contaIdSelected));
		

			// TODO criar padrão para tranferencia
			/*
			 * List<Padrao> padroes = padraoRN.findByMemo(lancamento.getMemo(),
			 * userSession.getContaApp()); if(padroes.isEmpty()){
			 * 
			 * Padrao padrao = new Padrao(); if(lancamento.isValorConsiderado()){
			 * padrao.setTipo(lancamento.getTipo()); }else{ padrao.setTipo(null); }
			 * 
			 * 
			 * padrao.setCodigo(GeradorCodigo.gerar());
			 * padrao.setContaApp(userSession.getContaApp()); padrao.setCreatedAt(new
			 * Date()); padrao.setMemo(lancamento.getMemo()); padrao.setUpdatedAt(new
			 * Date()); padrao.setValorConsiderado(lancamento.isValorConsiderado());
			 * padraoRN.insert(padrao); }
			 */
			eventoCategorizacao.fire(lancamento);

			UIService.hide("categorizacao_transfer_wvar");
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

	public List<Conta> getContas() {
		return contas;
	}

	public Long getContaIdSelected() {
		return contaIdSelected;
	}

	public void setContaIdSelected(Long contaIdSelected) {
		this.contaIdSelected = contaIdSelected;
	}

	

}