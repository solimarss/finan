package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PADRAO")
public class Padrao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "ID" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "MEMO", length = 300)
	private String memo;

	@Column(name = "VALOR_CONSIDERADO")
	private boolean valorConsiderado = true;

	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "TIPO_ID" )
	private Tipo tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "CONTAAPP_ID" )
	private ContaApp contaApp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public boolean isValorConsiderado() {
		return valorConsiderado;
	}

	public void setValorConsiderado(boolean valorConsiderado) {
		this.valorConsiderado = valorConsiderado;
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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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

}
