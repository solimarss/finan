package br.com.solimar.finan.enums;

public enum ContaTipoEnum {
	
	CHECKING_ACCOUNT("Conta Corrente"), 
	SAVINGS_ACCOUNT("Conta poupança"),
	CREDIT_CARD("Cartão de Credito"),
	WALLET("Dinheiro");

	private ContaTipoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
}
