package br.com.solimar.finan.enums;

public enum FilterLancTipoEnum {
	
	E("Receita"), 
	S("Despesa"),
	T("Todos");

	private FilterLancTipoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
	
}
