package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.solimar.finan.enums.ContaTipoEnum;

@Entity
@Table(name = "CONTA")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "NOME", length = 300)
	private String nome;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false, length = 30)
	private ContaTipoEnum tipo;

	@Column(name = "CONTA_NUMERO", length = 70)
	private String contaNumero;

	@Column(name = "BANCO_CODIGO", length = 70)
	private String bancoCodigo;

	@Column(name = "AGENCIA_NUMERO", length = 70)
	private String agenciaNumero;
	
	@Column(name = "LANCAMENTO_MANUAL")
	private boolean lancamentoManual = false;

	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	private ContaApp contaApp;
	
	
	public Conta() {
		
	}
	
	public Conta(Long id) {
		this.id = id;
	}

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

	public String getContaNumero() {
		return contaNumero;
	}

	public void setContaNumero(String contaNumero) {
		this.contaNumero = contaNumero;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public ContaTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(ContaTipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getBancoCodigo() {
		return bancoCodigo;
	}

	public void setBancoCodigo(String bancoCodigo) {
		this.bancoCodigo = bancoCodigo;
	}

	public boolean isLancamentoManual() {
		return lancamentoManual;
	}

	public void setLancamentoManual(boolean lancamentoManual) {
		this.lancamentoManual = lancamentoManual;
	}

}
