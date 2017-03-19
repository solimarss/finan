package br.com.solimar.finan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.solimar.finan.enums.ContaTipoEnum;

@Entity
public class ContaBancaria implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME", length = 300)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "CONTA_TIPO", nullable = false, length = 30)
	private ContaTipoEnum contaTipo;

	@Column(name = "CONTA_NUMERO", length = 70)
	private String contaNumero;

	@Column(name = "AGENCIA_NUMERO", length = 70)
	private String agenciaNumero;

	@ManyToOne(fetch = FetchType.EAGER)
	private ContaApp contaApp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ContaTipoEnum getContaTipo() {
		return contaTipo;
	}

	public void setContaTipo(ContaTipoEnum contaTipo) {
		this.contaTipo = contaTipo;
	}

	public String getContaNumero() {
		return contaNumero;
	}

	public void setContaNumero(String contaNumero) {
		this.contaNumero = contaNumero;
	}

	public String getAgenciaNumero() {
		return agenciaNumero;
	}

	public void setAgenciaNumero(String agenciaNumero) {
		this.agenciaNumero = agenciaNumero;
	}

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

}
