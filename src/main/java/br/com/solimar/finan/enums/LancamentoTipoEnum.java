package br.com.solimar.finan.enums;

public enum LancamentoTipoEnum {
	
	E("Receita"), 
	S("Despesa");

	private LancamentoTipoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
	
}
