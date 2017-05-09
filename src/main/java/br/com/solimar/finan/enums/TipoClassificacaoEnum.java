package br.com.solimar.finan.enums;

public enum TipoClassificacaoEnum {
	
	OF("Obrigatória Fixa"), 
	OV("Obrigatória Variável"),
	NOF("Não Obrigatória Fixa"), 
	NOV("Não Obrigatória Variável");

	private TipoClassificacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return this.descricao;
	}
}
