package br.com.solimar.finan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "FATURA")
public class Fatura implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "VALOR")
	private BigDecimal valor;

	@Column(name = "DATA_VENCIMENTO")
	@Temporal(value = TemporalType.DATE)
	private Date dataVencimento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONTA_ID")
	private Conta conta;

	@Column(name = "CREATED_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "UPDATE_AT")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedAt;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CONTAAPP_ID")
	private ContaApp contaApp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
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

	public ContaApp getContaApp() {
		return contaApp;
	}

	public void setContaApp(ContaApp contaApp) {
		this.contaApp = contaApp;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fatura other = (Fatura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
