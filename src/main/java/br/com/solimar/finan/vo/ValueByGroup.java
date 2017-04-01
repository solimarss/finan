package br.com.solimar.finan.vo;

import java.math.BigDecimal;

public class ValueByGroup {
	
	private String groupName;
	private BigDecimal valor;
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String categoriaNome) {
		this.groupName = categoriaNome;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
