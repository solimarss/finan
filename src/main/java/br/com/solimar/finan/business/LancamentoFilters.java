package br.com.solimar.finan.business;

import br.com.solimar.finan.entity.Conta;
import br.com.solimar.finan.entity.ContaApp;
import br.com.solimar.finan.enums.LancamentoTipoEnum;
import br.com.solimar.finan.enums.TipoClassificacaoEnum;

public class LancamentoFilters {

	private ContaApp contaApp;
	private Conta conta;
	private Long categoriaId;
	private Long tipoId;
	private TipoClassificacaoEnum classificacao;
	private LancamentoTipoEnum tipoES;
	private Boolean vlrConsiderado;
	private int mes;
	private int ano;

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	

	public TipoClassificacaoEnum getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(TipoClassificacaoEnum classificacao) {
		this.classificacao = classificacao;
	}

	public LancamentoTipoEnum getTipoES() {
		return tipoES;
	}

	public void setTipoES(LancamentoTipoEnum tipoES) {
		this.tipoES = tipoES;
	}

	public Boolean getVlrConsiderado() {
		return vlrConsiderado;
	}

	public void setVlrConsiderado(Boolean vlrConsiderado) {
		this.vlrConsiderado = vlrConsiderado;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

}
