package br.com.solimar.finan.enums;

public enum ContaTipoEnum {
	
	CHECKING_ACCOUNT("Conta Corrente"), 
	SAVINGS_ACCOUNT("Conta poupança"),
	WALLET("carteira");

	private ContaTipoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
}
