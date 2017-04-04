package br.com.solimar.finan.vo;

import java.math.BigDecimal;
import java.util.List;

public class ValueByGroup {
	
	
	private String groupName;
	private BigDecimal valor;
	private List<ValueByGroup> subGroup;
	private String superGroupName;
	
	
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
	
	public String getSuperGroupName() {
		return superGroupName;
	}
	public void setSuperGroupName(String superGroupName) {
		this.superGroupName = superGroupName;
	}
	public List<ValueByGroup> getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(List<ValueByGroup> subGroup) {
		this.subGroup = subGroup;
	}

}
