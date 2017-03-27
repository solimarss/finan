package br.com.solimar.finan.enums;

public enum ContaTipoEnum {
	
	CHECKING_ACCOUNT("Conta Corrente"), 
	SAVINGS_ACCOUNT("Conta poupan�a"),
	CREDIT_CARD("Cart�o de Credito"),
	WALLET("Dinheiro");

	private ContaTipoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
}
