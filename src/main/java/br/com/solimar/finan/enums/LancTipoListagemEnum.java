package br.com.solimar.finan.enums;

public enum LancTipoListagemEnum {
	
	E("Receita"), 
	S("Despesa"),
	X("Extrato de Conta"),
	T("Todos os Lançamentos");

	private LancTipoListagemEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
	
}
