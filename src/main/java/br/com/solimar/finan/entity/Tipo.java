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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.solimar.finan.enums.TipoClassificacaoEnum;

@Entity
@Table(name = "TIPO")
public class Tipo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@JoinColumn( name = "ID" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "NOME", length = 300)
	private String nome;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "CONTEGORIA_ID" )
	private Categoria categoria;

	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "CONTAAPP_ID" )
	private ContaApp contaApp;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CLASSIFICACAO", nullable = true, length = 30)
	private TipoClassificacaoEnum classificacao;

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

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Categoria getCategoria() {
		if (categoria == null) {
			categoria = new Categoria();
		}
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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

	public TipoClassificacaoEnum getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(TipoClassificacaoEnum classificacao) {
		this.classificacao = classificacao;
	}

	


}
